package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.DiscountCourseDTO;
import com.project.learnifyapp.dtos.DiscountDTO;
import com.project.learnifyapp.models.Discount;
import com.project.learnifyapp.models.DiscountCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CourseMapper.class, DiscountMapper.class})
public interface DiscountCourseMapper extends EntityMapper<DiscountCourseDTO, DiscountCourse>{

    @Override
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "isDelete", target = "isDelete")
    @Mapping(source = "discount.id", target = "discountId")
    DiscountCourseDTO toDTO (DiscountCourse entity);

    @Override
    @Mapping(source = "courseId", target = "course.id")
    @Mapping(source = "isDelete", target = "isDelete")
    @Mapping(source = "discountId", target = "discount.id")
    DiscountCourse toEntity (DiscountCourseDTO dto);
}
