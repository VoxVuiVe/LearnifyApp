package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.models.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, CartMapper.class, DiscountCourseMapper.class, FavouriteMapper.class})
public interface CourseMapper extends EntityMapper<CourseDTO, Course>{

    @Override
    @Mapping(source = "category.id", target = "categoryId")
    CourseDTO toDTO(Course entity);

    @Override
    @Mapping(source = "categoryId", target = "category.id")
    Course toEntity(CourseDTO dto);

}
