package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.RoleDTO;
import com.project.learnifyapp.models.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface RoleMapper extends EntityMapper<RoleDTO, Role>{
}
