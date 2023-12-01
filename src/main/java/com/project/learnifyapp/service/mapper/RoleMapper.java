package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.FavouriteDTO;
import com.project.learnifyapp.dtos.RoleDTO;
import com.project.learnifyapp.models.Favourite;
import com.project.learnifyapp.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", uses = {})
public interface RoleMapper extends EntityMapper<RoleDTO, Role>{

    @Mapping(target = "id", ignore = true)
    void updateFavouriteFromDTO(RoleDTO roleDTO, @MappingTarget Role role);
}
