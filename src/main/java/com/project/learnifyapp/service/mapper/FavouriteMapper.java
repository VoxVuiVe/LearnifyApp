package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.FavouriteDTO;
import com.project.learnifyapp.models.Favourite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class,CourseMapper.class})
public interface FavouriteMapper extends EntityMapper<FavouriteDTO, Favourite>{

    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "course.id", target = "courseId")
    FavouriteDTO toDTO(Favourite entity);

    @Override
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "courseId", target = "course.id")
    Favourite toEntity(FavouriteDTO dto);
}
