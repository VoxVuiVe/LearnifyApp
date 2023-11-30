package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.UserDTO;
import com.project.learnifyapp.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, User>{

    @Mapping(source = "role.id", target = "roleId")
    UserDTO toDTO(User entity);

    @Mapping(source = "roleId", target = "role.id")
    User toEntity(UserDTO dto);
}
