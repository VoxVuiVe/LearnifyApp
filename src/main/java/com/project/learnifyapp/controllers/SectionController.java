package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.SectionDTO;
import com.project.learnifyapp.exceptions.BadRequestAlertException;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.service.impl.SectionService;
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
public class SectionController {

    private final SectionService sectionService;

    @PostMapping("/section")
    public ResponseEntity<SectionDTO> createSection(@Valid @RequestBody SectionDTO sectionDTO) throws DataNotFoundException {
        if (sectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new section cannot already have an Id", ENTITY_NAME, "id exits");
        }
        SectionDTO result = sectionService.save(sectionDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PutMapping("/section/{id}")
    public ResponseEntity<SectionDTO> updateSection(@PathVariable Long id, @Valid @RequestBody SectionDTO sectionDTO) throws DataNotFoundException {
        if (sectionDTO.getId() != null && !Objects.equals(id, sectionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "id invalid");
        }

        if (!sectionService.exitsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        }

        SectionDTO result = sectionService.update(id, sectionDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @DeleteMapping("/section/{id}")
    public ResponseEntity<Void> deleteSection(@PathVariable Long id) throws DataNotFoundException {
        sectionService.remove(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/section/{id}")
    public ResponseEntity<SectionDTO> getSection(@PathVariable Long id) throws DataNotFoundException {
        SectionDTO sectionDTO = sectionService.getSection(id);
        return ResponseEntity
                .ok()
                .body(sectionDTO);
    }

    @GetMapping("/sections")
    public ResponseEntity<List<SectionDTO>> getAllSections() {
        List<SectionDTO> sections = sectionService.getAllSections();
        return  ResponseEntity
                .ok(sections);
    }
}
