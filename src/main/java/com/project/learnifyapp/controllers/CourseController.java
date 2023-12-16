package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.dtos.userDTO.CourseInfoDTO;
import com.project.learnifyapp.exceptions.BadRequestAlertException;
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

        return new ResponseEntity<>(response,HttpStatus.OK);
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

    @GetMapping("/course-info")
    public ResponseEntity<List<CourseInfoDTO>> getCourseInfo(){
        List<CourseInfoDTO> courseInfo = courseService.courseInfo();
        return ResponseEntity.ok(courseInfo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }
}
