package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.FavouriteDTO;
import com.project.learnifyapp.models.Favourite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class,CourseMapper.class})
public interface FavouriteMapper extends EntityMapper<FavouriteDTO, Favourite>{
}
