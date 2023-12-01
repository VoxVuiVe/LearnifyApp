package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.RoleDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Role;
import com.project.learnifyapp.repository.RoleRepository;
import com.project.learnifyapp.service.IRoleService;
import com.project.learnifyapp.service.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final Logger log = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;


    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        log.debug("Save new role: {}", roleDTO );

        Role role = roleMapper.toEntity(roleDTO);
        Role saveRole = roleRepository.save(role);

        log.debug("New role saved: {}", roleDTO);

        return roleMapper.toDTO(saveRole);
    }

    @Override
    public RoleDTO update(Long id, RoleDTO roleDTO) throws DataNotFoundException {
        log.debug("Update role with ID: {}", id);

        Role exitingRole = roleRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Role not found"));

        roleMapper.updateFavouriteFromDTO(roleDTO, exitingRole);
        Role updateRole = roleRepository.save(exitingRole);

        log.debug("update role: {}", updateRole);

        return roleMapper.toDTO(updateRole);
    }

    @Override
    public void remove(Long id) throws DataNotFoundException {
        log.debug("Remove role with ID: {}", id);

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Role not found"));

        roleRepository.delete(role);

        log.debug("Role remove: {}", role);
    }

    @Override
    public RoleDTO getRole(Long id) throws DataNotFoundException {
        log.debug("Fetching role with ID: {}", id);

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Role not found"));
        roleRepository.delete(role);

        log.debug("Role remove: {}", role);
        return roleMapper.toDTO(role);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toDTO).collect(Collectors.toList());
    }

    public boolean exitsById(Long id) {
        return roleRepository.existsById(id);
    }


}























