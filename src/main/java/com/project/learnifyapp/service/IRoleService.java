package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.RoleDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Role;

public interface IRoleService {
    Role createRole(RoleDTO roleDTO);
    Role getRoleById(Long roleId) throws DataNotFoundException;
    Role updateRole(Long roleId, RoleDTO updatedData) throws DataNotFoundException;
    void deleteRole(Long roleId) throws DataNotFoundException;
}
