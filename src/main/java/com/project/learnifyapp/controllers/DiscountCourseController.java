package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.DiscountCourseDTO;
import com.project.learnifyapp.repository.DiscountCourseRepository;
import com.project.learnifyapp.service.impl.DiscountCourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/discount-course/{id}")
    public void deleteDiscourse(@PathVariable long id){
        discountCourseService.deleteDiscountCourse(id);
    }
}
