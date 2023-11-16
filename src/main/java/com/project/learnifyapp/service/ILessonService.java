package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.LessonDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ILessonService {

    LessonDTO save(LessonDTO lessonDTO, MultipartFile videoFile) throws Exception;

    List<LessonDTO> getAllLessons();

    Optional<LessonDTO> findOne(Long id);

    void deleteLesson(Long id);
}
