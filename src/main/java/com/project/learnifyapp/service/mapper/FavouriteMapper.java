package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.CommentDTO;
import com.project.learnifyapp.dtos.FavouriteDTO;
import com.project.learnifyapp.models.Comment;
import com.project.learnifyapp.models.Favourite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring", uses = {UserMapper.class,CourseMapper.class})
public interface FavouriteMapper extends EntityMapper<FavouriteDTO, Favourite>{

    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "course.id", target = "courseId")
    FavouriteDTO toDTO(Favourite entity);

    @Override
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "courseId", target = "course.id")
    Favourite toEntity(FavouriteDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFavouriteFromDTO(FavouriteDTO favouriteDTO, @MappingTarget Favourite favourite);
}
