package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.LessonDTO;
import com.project.learnifyapp.exceptions.BadRequestAlertException;
import com.project.learnifyapp.models.Lesson;
import com.project.learnifyapp.repository.LessonRepository;
import com.project.learnifyapp.service.S3Service;
import com.project.learnifyapp.service.impl.LessonService;
import org.apache.tomcat.util.http.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("${api.prefix}/lessons")
public class LessonController {
    private final Logger log = LoggerFactory.getLogger(LessonController.class);

    private static final String ENTITY_NAME = "lessons";

    private final LessonService lessonService;

    private final LessonRepository lessonRepository;

    private final S3Service s3Service;

    public LessonController(LessonService lessonService, LessonRepository lessonRepository, S3Service s3Service) {
        this.lessonService = lessonService;
        this.lessonRepository = lessonRepository;
        this.s3Service = s3Service;
    }

    @PostMapping("")
    public ResponseEntity<LessonDTO> createLesson(@ModelAttribute LessonDTO lessonDTO, @RequestParam("videoFile") MultipartFile videoFile) {
        try {
            String videoUrl = s3Service.uploadVideoToS3(videoFile);
            lessonDTO.setVideoUrl(videoUrl);
            LessonDTO result = lessonService.save(lessonDTO, videoFile);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
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
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable(value = "id", required = false) final Long id,
                                                  @ModelAttribute LessonDTO lessonDTO, @RequestParam("videoFile") MultipartFile videoFile) throws Exception {
        log.debug("REST request to update Lesson :{}, {}, {}", id, lessonDTO, videoFile);
        if (lessonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lessonDTO.getId())) {
            throw new BadRequestAlertException("Invalid Id", ENTITY_NAME, "idinvalid");
        }
        if (!lessonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Lesson existingLesson = lessonRepository.findById(id).orElse(null);

        if (existingLesson == null) {
            throw new RuntimeException("Unable to find old Lesson ID: " + id);
        }

        lessonDTO.setVideoUrl(existingLesson.getVideoUrl());

        LessonDTO result = lessonService.save(lessonDTO, videoFile);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<LessonDTO>> getAllLessonList() {
        List<LessonDTO> lessonDTOs = lessonService.getAllLessons();
        return ResponseEntity.ok(lessonDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getLessonByID(@PathVariable Long id) {
        Optional<LessonDTO> lessonDTO = lessonService.findOneWithPresignedURL(id);
        return ResponseEntity.of(lessonDTO);
    }


    @GetMapping(value= "/page")
    public ResponseEntity<Map<String, Object>> getAllLesson(@RequestParam(name = "keyword", required = false) String keyword,
                                                            @RequestParam(name = "page") int page,
                                                            @RequestParam(name = "size") int size){
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<LessonDTO> lessons = lessonService.findAllPage(keyword, pageRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("lessons" , lessons.getContent());
        response.put("totalPages", lessons.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        try {
            lessonService.deleteLesson(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Trả về 204 NO CONTENT nếu xóa thành công
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Trả về 500 INTERNAL SERVER ERROR nếu có lỗi
        }
    }
}
