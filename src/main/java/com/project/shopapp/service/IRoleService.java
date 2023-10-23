package com.project.shopapp.service;

import com.project.shopapp.dtos.RoleDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Role;

public interface IRoleService {
    Role createRole(RoleDTO roleDTO);
    Role getRoleById(Long roleId) throws DataNotFoundException;
    Role updateRole(Long roleId, RoleDTO updatedData) throws DataNotFoundException;
    void deleteRole(Long roleId) throws DataNotFoundException;
}
