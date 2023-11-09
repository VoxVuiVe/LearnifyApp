package com.project.shopapp.service.impl;
 

import com.project.shopapp.dtos.UserRoleDTO;
 import com.project.shopapp.models.Course;
import com.project.shopapp.models.User;
import com.project.shopapp.models.UserRole;
import com.project.shopapp.repository.UserRoleRepository;
import com.project.shopapp.service.IUserRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleService implements IUserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService (UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    private UserRoleDTO convertToUserRoleDTO(UserRole userRole) {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserRoleId(userRole.getUserRoleId());
        userRoleDTO.setUserId(userRole.getUser().getUserId());
        userRoleDTO.setCourseId(userRole.getCourse().getCourseId());
        return userRoleDTO;
    }

    @Override
    public UserRoleDTO addUserRole(UserRoleDTO userRoleDTO) {
        UserRole userRole = new UserRole();
        // Set user and course based on IDs from DTO
        userRole.setUser(new User());
        userRole.setCourse(new Course());

        UserRole savedUserRole = userRoleRepository.save(userRole);
        return convertToUserRoleDTO(savedUserRole);
    }

    @Override
    public UserRoleDTO getUserRole(Long userRoleId) throws NotFoundException {
        UserRole userRole = userRoleRepository.findById(userRoleId)
                .orElseThrow(() -> new NotFoundException());
        return convertToUserRoleDTO(userRole);
    }

    @Override
    public UserRoleDTO updateUserRole(Long userRoleId, UserRoleDTO userRoleDTO) throws NotFoundException {
        UserRole existingUserRole = userRoleRepository.findById(userRoleId)
                .orElseThrow(() -> new NotFoundException());

        // Update fields
        existingUserRole.setUser(new User());
        existingUserRole.setCourse(new Course());

        UserRole updatedUserRole = userRoleRepository.save(existingUserRole);
        return convertToUserRoleDTO(updatedUserRole);
    }

    @Override
    public void deleteUserRole(Long userRoleId) {
        userRoleRepository.deleteById(userRoleId);
    }

    @Override
    public List<UserRoleDTO> getAllUserRoles() {
        List<UserRole> userRoles = userRoleRepository.findAll();
        return userRoles.stream()
                .map(this::convertToUserRoleDTO)
                .collect(Collectors.toList());
    }
}

