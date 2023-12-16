package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.RatingDTO;
import com.project.learnifyapp.models.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CourseMapper.class,UserMapper.class})
public interface RatingMapper extends EntityMapper<RatingDTO, Rating>{
    @Override
    @Mapping(source = "course.id" ,target = "courseId")
    @Mapping(source = "user.id" ,target = "userId")
    RatingDTO toDTO(Rating entity);

    @Override
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "courseId", target = "course.id")
    Rating toEntity(RatingDTO dto);

    void updateRatingFromDTO(RatingDTO ratingDTO, @MappingTarget Rating rating);
}
