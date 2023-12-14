package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.CourseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICourseService {
    CourseDTO save(CourseDTO courseDTO);

    @Transactional(readOnly = true)
    List<CourseDTO> findAll();

    Page<CourseDTO> findAllPage(String keyword, PageRequest pageRequest);


    @Transactional(readOnly = true)
    CourseDTO findOne(Long id);

    void deleteCourse(Long id);
}
