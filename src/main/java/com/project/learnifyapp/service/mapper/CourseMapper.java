package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.models.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UserMapper.class, SectionMapper.class})
public interface CourseMapper extends EntityMapper<CourseDTO, Course>{

    @Override
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "user.id", target = "userId")
    CourseDTO toDTO(Course entity);

    @Override
    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "userId", target = "user.id")
    Course toEntity(CourseDTO dto);

}
