package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.dtos.CourseImageDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.exceptions.InvalidParamException;
import com.project.learnifyapp.models.*;
import com.project.learnifyapp.repository.*;
import com.project.learnifyapp.service.ICourseService;
import com.project.learnifyapp.service.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
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

    private final CourseImageRepository courseImageRepository;

    private static String UPLOADS_FOLDER = "uploads";

    @Override
    public CourseDTO save(CourseDTO courseDTO) throws DataNotFoundException {
        log.debug("Save new course: {}", courseDTO);
        Category category = categoryReponsitory.findById(courseDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found"));

        User user = userRepository.findById(courseDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Course course = courseMapper.toEntity(courseDTO);
        course.setCategory(category);
        course.setUser(user);

        Course saveCourse = courseRepository.save(course);

        log.debug("New course saved: {}", saveCourse);
        return courseMapper.toDTO(saveCourse);
    }

    @Override
    public CourseImage createCourseImage(Long courseId, CourseImageDTO courseImageDTO) throws Exception {
        Course existingCourse = courseRepository
                .findById(courseId)
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find product with id: "+courseImageDTO.getProductId()));
        CourseImage newCourseImage = CourseImage.builder()
                .course(existingCourse)
                .imageUrl(courseImageDTO.getImageUrl())
                .build();
        //Ko cho insert quá 5 ảnh cho 1 sản phẩm
        int size = courseImageRepository.findByCourseId(courseId).size();
        if(size >= CourseImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException(
                    "Number of images must be <= "
                            +CourseImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        if (existingCourse.getThumbnail() == null ) {
            existingCourse.setThumbnail(newCourseImage.getImageUrl());
        }
        courseRepository.save(existingCourse);
        return courseImageRepository.save(newCourseImage);
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

        Course updatedCourse = courseRepository.save(existingCourse);

        log.debug("Updated comment: {}", updatedCourse);

        return courseMapper.toDTO(updatedCourse);
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
    @Transactional(readOnly = true)
    public CourseDTO findOne(Long id){
        Optional<Course> course = courseRepository.findById(id);
        return course.map(courseMapper::toDTO).orElse(null);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
        if (!course.getSection().isEmpty()) {
            course.setIsDelete(false);
            courseRepository.save(course);
        } else {
            courseRepository.delete(course);
        }
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

    public boolean existsById(Long id) {
        return courseRepository.existsById(id);
    }

    private boolean isImageFile(MultipartFile file) {
        String contenType = file.getContentType();
        return contenType != null && contenType.startsWith("image/");
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        if(!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException(("Invalid image format"));
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // đường dẫn đến sthuw mục mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get(UPLOADS_FOLDER);
        // Kiểm tra và tạo th mục nê nó không tồn tại
        if(!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
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
