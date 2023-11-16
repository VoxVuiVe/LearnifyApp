package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.CourseCartDTO;
import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.models.CourseCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CourseMapper.class, CartMapper.class})
public interface CourseCartMapper extends EntityMapper<CourseCartDTO, CourseCart>{

    @Override
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "cart.id", target = "cartId")
    CourseCartDTO toDTO(CourseCart entity);


}
