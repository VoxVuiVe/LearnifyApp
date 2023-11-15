package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.RoleDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Role;
import com.project.learnifyapp.repository.RoleRepository;
import com.project.learnifyapp.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role createRole(RoleDTO roleDTO) {
        Role newRole = Role.builder()
                .name(roleDTO.getName())
                .build();

        return roleRepository.save(newRole);
    }

    @Override
    public Role getRoleById(Long roleId) throws DataNotFoundException {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new DataNotFoundException("Role không tồn tại!"));
    }

    @Override
    public Role updateRole(Long roleId, RoleDTO updatedData) throws DataNotFoundException {
        Role role = getRoleById(roleId);

        // Update the role fields based on the updatedData
        role.setName(updatedData.getName());

        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) throws DataNotFoundException {
        Role role = getRoleById(roleId);
        roleRepository.delete(role);
    }
}
