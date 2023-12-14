package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.models.CartItem;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.repository.CartItemRepository;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.service.ICourseService;
import com.project.learnifyapp.service.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final CartItemRepository cartItemRepository;

    private final CourseMapper courseMapper;

    @Override
    public CourseDTO save(CourseDTO courseDTO) {
        log.debug("Request to save Course: {}", courseDTO);

        Course course = courseMapper.toEntity(courseDTO);
        Course savedCourse = courseRepository.saveAndFlush(course);
        try {
            if (savedCourse.getId() != null) {
                savedCourse = courseRepository.save(savedCourse);
                CourseDTO result = courseMapper.toDTO(savedCourse);
                return result;
            }
        } catch (Exception e) {
            throw new RuntimeException("ERROR: Can't update course");
        }
        return courseMapper.toDTO(savedCourse);
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
}
