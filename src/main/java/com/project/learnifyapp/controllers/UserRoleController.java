package com.project.learnifyapp.controllers;
 
import com.project.learnifyapp.dtos.UserRoleDTO;
import com.project.learnifyapp.service.IUserRoleService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

 import java.util.List;

@RestController
@RequestMapping("/api/userRoles")
public class UserRoleController {

    private final IUserRoleService userRoleService;

    @Autowired
    public UserRoleController(IUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping
    public ResponseEntity<UserRoleDTO> addUserRole(@RequestBody @Valid UserRoleDTO userRoleDTO) {
        UserRoleDTO savedUserRole = userRoleService.addUserRole(userRoleDTO);
        return new ResponseEntity<>(savedUserRole, HttpStatus.CREATED);
    }

    @GetMapping("/{userRoleId}")
    public ResponseEntity<UserRoleDTO> getUserRole(@PathVariable Long userRoleId) throws NotFoundException {
        UserRoleDTO userRoleDTO = userRoleService.getUserRole(userRoleId);
        return ResponseEntity.ok(userRoleDTO);
    }

    @PutMapping("/{userRoleId}")
    public ResponseEntity<UserRoleDTO> updateUserRole(@PathVariable Long userRoleId, @RequestBody @Valid UserRoleDTO userRoleDTO) throws NotFoundException {
        UserRoleDTO updatedUserRole = userRoleService.updateUserRole(userRoleId, userRoleDTO);
        return ResponseEntity.ok(updatedUserRole);
    }

    @DeleteMapping("/{userRoleId}")
    public ResponseEntity<Void> deleteUserRole(@PathVariable Long userRoleId) {
        userRoleService.deleteUserRole(userRoleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserRoleDTO>> getAllUserRoles() {
        List<UserRoleDTO> allUserRoles = userRoleService.getAllUserRoles();
        return ResponseEntity.ok(allUserRoles);
    }
}

