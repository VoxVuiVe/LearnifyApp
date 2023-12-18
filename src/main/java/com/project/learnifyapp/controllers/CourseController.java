package com.project.learnifyapp.controllers;

import com.project.learnifyapp.components.LocalizationUtils;
import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.dtos.CourseImageDTO;
import com.project.learnifyapp.dtos.LessonDTO;
import com.project.learnifyapp.dtos.userDTO.CourseInfoDTO;
import com.project.learnifyapp.exceptions.BadRequestAlertException;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.CourseImage;
import com.project.learnifyapp.models.Lesson;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.service.S3Service;
import com.project.learnifyapp.service.impl.CourseService;
import com.project.learnifyapp.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/courses")
public class CourseController {
    private final Logger log = LoggerFactory.getLogger(CourseController.class);

    private static final String ENTITY_NAME = "courses";

    private final CourseService courseService;

    private final CourseRepository courseRepository;

    private final LocalizationUtils localizationUtils;

    private final S3Service s3Service;


    @PostMapping("")
    public ResponseEntity<CourseDTO> createCourse(@ModelAttribute CourseDTO courseDTO, @RequestParam("imageFile") MultipartFile imageFile)  {
//        log.debug("REST request to save Course: {}", courseDTO);
//        CourseDTO result = courseService.save(courseDTO);
//        return ResponseEntity.ok()
//                .body(result);
        try {
            String imageUrl = s3Service.uploadImagesToS3(imageFile);
            courseDTO.setThumbnail(imageUrl);
            CourseDTO result = courseService.save(courseDTO, imageFile);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/id")
                    .buildAndExpand(result.getId())
                    .toUri();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(location);
            return new ResponseEntity<>(result, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateSection(@PathVariable(value = "id", required = false) final Long id,
                                                  @ModelAttribute CourseDTO courseDTO, @RequestParam("imageFile") MultipartFile imageFile)throws Exception{
        log.debug("REST request to update Course :{}, {}, {}", id, courseDTO, imageFile);
        if (courseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "id invalid");
        }
        if (!courseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        }
        Course existingCourse = courseRepository.findById(id).orElse(null);

        if (existingCourse == null) {
            throw new RuntimeException("Unable to find old Course ID: " + id);
        }

        courseDTO.setThumbnail(existingCourse.getThumbnail());

        CourseDTO result = courseService.save(courseDTO, imageFile);
        return ResponseEntity.ok().body(result);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseDTO courseDTO) throws DataNotFoundException {
//        if (courseDTO.getId() != null && !Objects.equals(id, courseDTO.getId())) {
//            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "id invalid");
//        }
//        if (!courseService.existsById(id)) {
//            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
//        }
//
//        CourseDTO result = courseService.update(id, courseDTO);
//        return ResponseEntity
//                .ok()
//                .body(result);
//    }

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

    @GetMapping("/course-info")
    public ResponseEntity<List<CourseInfoDTO>> getCourseInfo(){
        List<CourseInfoDTO> courseInfo = courseService.courseInfo();
        return ResponseEntity.ok(courseInfo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity
                .noContent()
                .build();
    }

//    @GetMapping("/pages")
//    public ResponseEntity<Map<String, Object>> getCoursePage(
//            @RequestParam(name = "keyword",required = false,defaultValue = "") String keyword,
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "10") int size ){
//        PageRequest pageRequest = PageRequest.of(page,size);
//        Page<CourseDTO> courses = courseService.findAllPage(keyword,pageRequest);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("courses",courses.getContent());
//        response.put("totalPages",courses.getTotalPages());
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

}
