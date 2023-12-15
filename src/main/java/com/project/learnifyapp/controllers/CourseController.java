package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.CommentDTO;
import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.exceptions.BadRequestAlertException;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.service.impl.CourseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

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
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) throws DataNotFoundException {
        log.debug("REST request to save Course: {}", courseDTO);
        CourseDTO result = courseService.save(courseDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseDTO courseDTO) throws DataNotFoundException {
        if (courseDTO.getId() != null && !Objects.equals(id, courseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "id invalid");
        }
        if (!courseService.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        }

        CourseDTO result = courseService.update(id, courseDTO);
        return ResponseEntity
                .ok()
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/pages")
    public ResponseEntity<Map<String, Object>> getCoursePage(
            @RequestParam(name = "keyword",required = false,defaultValue = "") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size ){
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<CourseDTO> courses = courseService.findAllPage(keyword,pageRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("courses",courses.getContent());
        response.put("totalPages",courses.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
