package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.DiscountCourseDTO;
import com.project.learnifyapp.dtos.DiscountDTO;
import com.project.learnifyapp.exceptions.BadRequestAlertException;
import com.project.learnifyapp.models.DiscountCourse;
import com.project.learnifyapp.repository.DiscountCourseRepository;
import com.project.learnifyapp.service.impl.DiscountCourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DiscountCourseController {
    private final DiscountCourseService discountCourseService;
    private final DiscountCourseRepository discountCourseRepository;

    @PostMapping("/discount-course")
    public ResponseEntity<DiscountCourseDTO> createDiscountCourse(@Valid @RequestBody DiscountCourseDTO discountCourseDTO){
        DiscountCourseDTO result = discountCourseService.createDiscountCourse(discountCourseDTO);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/discount-course/{id}")
    public ResponseEntity<DiscountCourseDTO> updateDiscount(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody DiscountCourseDTO discountCourseDTO){
        if(discountCourseDTO.getId() == null){
            throw new BadRequestAlertException("Invalid id",ENTITY_NAME,"idnull");
        }
        if (!Objects.equals(id, discountCourseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!discountCourseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DiscountCourseDTO result = discountCourseService.createDiscountCourse(discountCourseDTO);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/discount-course/{id}/{isDelete}")
    @Transactional
    public ResponseEntity<?> updateDiscountCourse(@PathVariable Long id, @PathVariable Boolean isDelete) {
        try {
            discountCourseService.updateDiscountCourse(id,isDelete);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @DeleteMapping("/discount-course/{id}")
    public void deleteDiscourse(@PathVariable long id){
        discountCourseService.deleteDiscountCourse(id);
    }
}
