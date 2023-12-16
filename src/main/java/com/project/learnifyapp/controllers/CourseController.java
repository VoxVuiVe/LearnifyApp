package com.project.learnifyapp.controllers;

import com.project.learnifyapp.components.LocalizationUtils;
import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.dtos.CourseImageDTO;
import com.project.learnifyapp.dtos.UserImageDTO;
import com.project.learnifyapp.exceptions.BadRequestAlertException;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.CourseImage;
import com.project.learnifyapp.models.UserImage;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.service.impl.CourseService;
import com.project.learnifyapp.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/courses")
public class CourseController {
    private final Logger log = LoggerFactory.getLogger(CourseController.class);

    private static final String ENTITY_NAME = "courses";

    private final CourseService courseService;

    private final CourseRepository courseRepository;

    private final LocalizationUtils localizationUtils;


    @PostMapping("")
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) throws DataNotFoundException {
        log.debug("REST request to save Course: {}", courseDTO);
        CourseDTO result = courseService.save(courseDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping(value = "/uploads/{courseId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@PathVariable("courseId") Long courseId, @RequestParam("files")List<MultipartFile> files) throws Exception {
        try {
            Course existingCourse = courseService.getCourseById(courseId);
            files = files == null ? new ArrayList<>() : files;
            if (files.size() > CourseImage.MAXIMUM_IMAGES_PER_PRODUCT) {
                return ResponseEntity.badRequest().body(localizationUtils
                        .getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_MAX_1));
            }
            List<CourseImage> productImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue;
                }
                //Kiểm tra kich thước file và định dạng
                if(file.getSize() > 10 * 1024 * 1024) { // kích thước > 10mb
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body(localizationUtils
                                    .getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_FILE_LARGE));
                }

                // lưu file và cập nhật thumnail trong DTO
                String filename = courseService.storeFile(file); // Thay thế hàm này với code của bạn để lưu file
                // lưu vào đối tượng product trong DB
                CourseImage productImage = courseService.createCourseImage(
                        existingCourse.getId(),
                        CourseImageDTO.builder()
                                .imageUrl(filename)
                                .build()
                );
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

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
