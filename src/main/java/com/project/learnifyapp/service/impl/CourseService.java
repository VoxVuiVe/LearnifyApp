package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.dtos.userDTO.CourseInfoDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.*;
import com.project.learnifyapp.repository.*;
import com.project.learnifyapp.service.ICourseService;
import com.project.learnifyapp.service.S3Service;
import com.project.learnifyapp.service.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService implements ICourseService {

    private final Logger log = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    private final CategoryReponsitory categoryReponsitory;

    private final UserRepository userRepository;

    private final S3Service s3Service;

    @Override
    public CourseDTO save(CourseDTO courseDTO, MultipartFile imageFile) {
        log.debug("Request to save Course: {}", courseDTO);

        Course course = courseMapper.toEntity(courseDTO);

        // Lưu entity vào cơ sở dữ liệu
        Course savedCourse = courseRepository.saveAndFlush(course);

        log.debug("New Course saved: {}", course);
        return courseMapper.toDTO(course);
    }

    @Override
    public CourseDTO update(Long Id, CourseDTO courseDTO) throws DataNotFoundException {
        log.debug("Update comment with ID: {}", Id);

        Course existingCourse = courseRepository.findById(Id)
                .orElseThrow(() -> new DataNotFoundException("Course not found"));

        Category category = categoryReponsitory.findById(courseDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found"));

        User user = userRepository.findById(courseDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        courseMapper.updateCourseFromDTO(courseDTO, existingCourse);
        existingCourse.setCategory(category);
        existingCourse.setUser(user);
        if(existingCourse.getThumbnail() != null) {
            existingCourse.setThumbnail(courseDTO.getThumbnail());
        }

        Course updatedCourse = courseRepository.save(existingCourse);

        log.debug("Updated comment: {}", updatedCourse);

        return courseMapper.toDTO(updatedCourse);
    }

    @Override
    public Course getCourseById(Long courseId) throws Exception {
        Optional<Course> optionalCourse = courseRepository.getDetailCourse(courseId);
        if(optionalCourse.isPresent()) {
            return optionalCourse.get();
        }
        throw new DataNotFoundException("Cannot find course with ID: " + courseId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseDTO> findOneWithPresignedImageURL(Long id) {
        Optional<CourseDTO> courseDTO = courseRepository.findById(id).map(courseMapper::toDTO);
        if (courseDTO.isPresent()) {
            CourseDTO course = courseDTO.get();
            String imageKey = course.getThumbnail(); // Giả định rằng getImageUrl trả về key

            // Kiểm tra nullability của videoKey
            if (imageKey != null) {
                // Sử dụng S3Service để lấy presigned URL
                String getPresignedURL = s3Service.getPresignedURL(imageKey);

                // Cập nhật đường dẫn image trong CourseDTO với presigned URL
                course.setThumbnail(getPresignedURL);

                return Optional.of(course);
            } else {
                // Xử lý trường hợp ImageKey là null, nếu cần
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseDTO> findAll(){
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CourseDTO> findAllPage(String keyword, PageRequest pageRequest) {
        if(keyword.equals("")) {
            keyword = null;
        }
        Page<Course> discountPage = courseRepository.searchCategory(keyword,pageRequest);
        Page<CourseDTO> dtoPage = discountPage.map(this::convertToDto);
        return dtoPage;
    }

    @Override
    public List<CourseInfoDTO> courseInfo() {
        List<Object[]> queryResult = courseRepository.getCoursesInfo();
        System.out.println("result==" + queryResult);
        // Ánh xạ trực tiếp trong phương thức
        List<CourseInfoDTO> result = new ArrayList<>();
        for (Object[] array : queryResult) {
            CourseInfoDTO courseInfoDTO = new CourseInfoDTO();
            courseInfoDTO.setCourseId((Long) array[0]);
            courseInfoDTO.setThumbnail((String) array[1]);
            courseInfoDTO.setCreateAt((String) array[2].toString());
            courseInfoDTO.setTitle((String) array[3]);
            courseInfoDTO.setFullname((String) array[4]);
            courseInfoDTO.setImageUrl((String) array[5]);
            courseInfoDTO.setSectionsId((Long) array[6]);
            courseInfoDTO.setQuantityLesson((Integer) array[7]);
            result.add(courseInfoDTO);
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDTO findOne(Long id){
        Optional<Course> course = courseRepository.findById(id);
        return course.map(courseMapper::toDTO).orElse(null);
    }

    @Override
    public void remove(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
        if (!course.getSection().isEmpty()) {
            course.setIsDelete(false);

            courseRepository.save(course);

            s3Service.deleteFile(course.getThumbnail());
        } else {
            courseRepository.delete(course);
        }
    }

    public boolean existsById(Long id) {
        return courseRepository.existsById(id);
    }

    private CourseDTO convertToDto(Course discount) {
        CourseDTO dto = new CourseDTO();
        dto.setId(discount.getId());
        dto.setTitle(discount.getTitle());
        dto.setPrice(discount.getPrice());
        dto.setStartTime(discount.getStartTime());
        dto.setEndTime(discount.getEndTime());
        dto.setEnrollmentCount(discount.getEnrollmentCount());
        dto.setThumbnail(discount.getThumbnail());
        dto.setIsDelete(discount.getIsDelete());
        dto.setCategoryId(discount.getCategory().getId());
        dto.setUserId(discount.getUser().getId());
        dto.setDescription(discount.getDescription());
        return dto;
    }
}
