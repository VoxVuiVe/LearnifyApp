package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.DiscountCourseDTO;
import com.project.learnifyapp.service.impl.DiscountCourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DiscountCourseController {
    private final DiscountCourseService discountCourseService;

    @PostMapping("discount-course")
    public ResponseEntity<DiscountCourseDTO> createDiscountCourse(@Valid @RequestBody DiscountCourseDTO discountCourseDTO){
        DiscountCourseDTO result = discountCourseService.createDiscountCourse(discountCourseDTO);
        return ResponseEntity.ok().body(result);
    }

}
