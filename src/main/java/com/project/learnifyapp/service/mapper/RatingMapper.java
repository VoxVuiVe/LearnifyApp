package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.RatingDTO;
import com.project.learnifyapp.models.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface RatingMapper extends EntityMapper<RatingDTO, Rating>{
    @Override
    @Mapping(source = "course.id" ,target = "courseId")
    @Mapping(source = "user.id" ,target = "userId")
    RatingDTO toDTO(Rating entity);

}
