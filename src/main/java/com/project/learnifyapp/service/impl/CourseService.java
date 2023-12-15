package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.CommentDTO;
import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.*;
import com.project.learnifyapp.repository.CartItemRepository;
import com.project.learnifyapp.repository.CategoryReponsitory;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.repository.UserRepository;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
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

        log.debug("Updated comment: {}", existingCourse);

        return courseMapper.toDTO(existingCourse);
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
