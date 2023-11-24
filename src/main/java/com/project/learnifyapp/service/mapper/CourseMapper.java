package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.models.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, CartMapper.class, DiscountMapper.class, FavouriteMapper.class})
public interface CourseMapper extends EntityMapper<CourseDTO, Course>{
}
