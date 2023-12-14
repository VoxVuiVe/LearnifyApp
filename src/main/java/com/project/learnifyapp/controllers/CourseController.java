package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.exceptions.BadRequestAlertException;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.service.impl.CourseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("${api.prefix}/courses")
public class CourseController {
    private final Logger log = LoggerFactory.getLogger(CourseController.class);

    private static final String ENTITY_NAME = "courses";

    private final CourseService courseService;

    private final CourseRepository courseRepository;

    public CourseController (CourseService courseService, CourseRepository courseRepository){
        this.courseRepository = courseRepository;
        this.courseService = courseService;
    }

    @PostMapping("")
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO){
        log.debug("REST request to save Course: {}", courseDTO);
        CourseDTO result = courseService.save(courseDTO);
        return ResponseEntity.ok()
                .header(ENTITY_NAME, result.getId().toString())
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable(value = "id", required = false) final Long id,
                                                  @Valid @RequestBody CourseDTO courseDTO){
        log.debug("REST request to update course: {}", courseDTO);
        if (courseDTO.getId() == null){
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idNull");
        }
        if (!Objects.equals(id, courseDTO.getId())){
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idInvalid");
        }
        if (!courseRepository.existsById(id)){
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idNotFound");
        }
        CourseDTO result = courseService.save(courseDTO);
        return ResponseEntity.ok()
                .header(ENTITY_NAME, result.getId().toString())
                .body(result);
    }

    @GetMapping("")
    public ResponseEntity<List<CourseDTO>> getAllCourse(){
        List<CourseDTO> courseDTOS = courseService.findAll();
        return ResponseEntity.ok(courseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id){
        CourseDTO courseDTO = courseService.findOne(id);
        if (courseDTO != null){
            return ResponseEntity.ok(courseDTO);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }
}
