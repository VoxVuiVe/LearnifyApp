package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.DiscountDTO;
import com.project.learnifyapp.models.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface DiscountMapper extends EntityMapper<DiscountDTO, Discount> {

    @Override
    @Mapping(source = "course.id", target = "courseId")
    DiscountDTO toDTO (Discount entity);

    @Override
    @Mapping(source = "courseId", target = "course.id")
    Discount toEntity (DiscountDTO dto);
}
