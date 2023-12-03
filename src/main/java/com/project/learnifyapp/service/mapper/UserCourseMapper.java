package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.UserCourseDTO;
import com.project.learnifyapp.models.UserCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface UserCourseMapper extends EntityMapper<UserCourseDTO, UserCourse>{

    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "course.id", target = "courseId")
    UserCourseDTO toDTO(UserCourse entity);

    @Override
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "courseId", target = "course.id")
    UserCourse toEntity(UserCourseDTO dto);

}
