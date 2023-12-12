package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.DiscountCourseDTO;
import com.project.learnifyapp.models.DiscountCourse;

public interface IDiscountCourseService {
    DiscountCourseDTO createDiscountCourse(DiscountCourseDTO discountCourseDTO);
    void deleteDiscountCourse(Long id);

    void updateDiscountCourse(Long id, Boolean isDelete);
}
