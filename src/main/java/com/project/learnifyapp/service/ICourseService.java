package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.CommentDTO;
import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.dtos.CourseImageDTO;
import com.project.learnifyapp.dtos.userDTO.CourseInfoDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.CourseImage;
import com.project.learnifyapp.models.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ICourseService {
    CourseDTO save(CourseDTO courseDTO) throws DataNotFoundException;

    CourseImage createCourseImage(Long courseId, CourseImageDTO courseImageDTO) throws Exception;

    Course getCourseById(Long courseId) throws Exception;

    CourseDTO update(Long Id, CourseDTO courseDTO) throws DataNotFoundException;

    @Transactional(readOnly = true)
    List<CourseDTO> findAll();

    Page<CourseDTO> findAllPage(String keyword, PageRequest pageRequest);

    @Transactional(readOnly = true)
    List<CourseInfoDTO> courseInfo();

    @Transactional(readOnly = true)
    CourseDTO findOne(Long id);

    void deleteCourse(Long id);

    String storeFile(MultipartFile file) throws IOException;

}
