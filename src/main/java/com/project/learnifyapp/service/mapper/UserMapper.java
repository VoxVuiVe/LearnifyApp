package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.UserDTO;
import com.project.learnifyapp.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<UserDTO, User>{
}
