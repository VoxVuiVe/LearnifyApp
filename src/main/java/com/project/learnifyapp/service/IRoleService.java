package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.RoleDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Role;

import java.util.List;

public interface IRoleService {
    RoleDTO save(RoleDTO roleDTO);
    RoleDTO update(Long roleId, RoleDTO updatedData) throws DataNotFoundException;
    void remove(Long roleId) throws DataNotFoundException;
    Role getRole(Long roleId) throws DataNotFoundException;
    List<RoleDTO> getAllRoles();

}
