package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.service.ICourseService;
import com.project.learnifyapp.service.mapper.CourseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService implements ICourseService {

    private final Logger log = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    @Autowired
    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper){
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    public CourseDTO save(CourseDTO courseDTO){
        log.debug("Request to save Course: {}", courseDTO);
        Course course = courseMapper.toEntity(courseDTO);
        course = courseRepository.save(course);
        return courseMapper.toDTO(course);
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
}
