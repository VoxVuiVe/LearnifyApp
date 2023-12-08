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

    private final RoleRepository roleRepository;
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}























