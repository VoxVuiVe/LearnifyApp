package com.project.shopapp.service;
 

import com.project.shopapp.dtos.UserRoleDTO;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface IUserRoleService {

    UserRoleDTO addUserRole(UserRoleDTO userRoleDTO);

    UserRoleDTO getUserRole(Long userRoleId) throws NotFoundException;

    UserRoleDTO updateUserRole(Long userRoleId, UserRoleDTO userRoleDTO) throws NotFoundException;

    void deleteUserRole(Long userRoleId);

    List<UserRoleDTO> getAllUserRoles();
}

