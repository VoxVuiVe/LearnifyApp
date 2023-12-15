package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.CommentDTO;
import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICourseService {
//    CourseDTO save(CourseDTO courseDTO);

    CourseDTO save(CourseDTO courseDTO) throws DataNotFoundException;

    CourseDTO update(Long Id, CourseDTO courseDTO) throws DataNotFoundException;

    @Transactional(readOnly = true)
    List<CourseDTO> findAll();

    @Transactional(readOnly = true)
    CourseDTO findOne(Long id);

    void deleteCourse(Long id);

    Page<CourseDTO> findAllPage(String keyword, PageRequest pageRequest);
}
