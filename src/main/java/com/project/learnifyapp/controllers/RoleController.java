package com.project.learnifyapp.controllers;



import com.project.learnifyapp.dtos.RoleDTO;
import com.project.learnifyapp.exceptions.BadRequestAlertException;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.service.impl.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@RestController
@RequestMapping("${api.prefix}/")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;


    @PostMapping("/role")
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        if (roleDTO.getId() != null) {
            throw new BadRequestAlertException("A new  Role cannot already have an Id", ENTITY_NAME, "id exits");
        }

        RoleDTO result = roleService.save(roleDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<RoleDTO> updateRole(@Valid @PathVariable Long id, @RequestBody RoleDTO roleDTO) throws DataNotFoundException {
        if (roleDTO.getId() != null && Objects.equals(id, roleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "id invalid");
        }

        if (!roleService.exitsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        }

        RoleDTO result = roleService.update(id, roleDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) throws DataNotFoundException {
        roleService.remove(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<RoleDTO> getRole(@PathVariable Long id) throws DataNotFoundException {
        RoleDTO roleDTO = roleService.getRole(id);
        return ResponseEntity
                .ok()
                .body(roleDTO);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return ResponseEntity
                .ok(roles);
    }
}




























