package com.project.shopapp.controllers;

import com.project.shopapp.dtos.RoleDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Role;
import com.project.shopapp.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    
    private final IRoleService roleService;

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody RoleDTO roleDTO) {
        Role createdRole = roleService.createRole(roleDTO);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long roleId) throws DataNotFoundException {
        Role role = roleService.getRoleById(roleId);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable Long roleId, @RequestBody RoleDTO updatedData)
            throws DataNotFoundException {
        Role updatedRole = roleService.updateRole(roleId, updatedData);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long roleId) throws DataNotFoundException {
        roleService.deleteRole(roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
