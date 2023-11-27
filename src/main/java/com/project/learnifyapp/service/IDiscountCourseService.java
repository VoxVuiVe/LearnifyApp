package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.DiscountCourseDTO;
import com.project.learnifyapp.models.DiscountCourse;

public interface IDiscountCourseService {
    DiscountCourse createDiscountCourse(DiscountCourseDTO discountCourseDTO);

}
