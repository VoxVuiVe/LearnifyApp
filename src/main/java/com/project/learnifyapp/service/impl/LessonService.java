package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.LessonDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Lesson;
import com.project.learnifyapp.repository.LessonRepository;
import com.project.learnifyapp.service.ILessonService;
import com.project.learnifyapp.service.S3Service;
import com.project.learnifyapp.service.mapper.LessonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LessonService implements ILessonService {

    private final Logger log = LoggerFactory.getLogger(LessonService.class);
    private final S3Service s3Service;
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    @Autowired
    public LessonService(LessonRepository lessonRepository, LessonMapper lessonMapper, S3Service s3Service) {
        this.lessonRepository = lessonRepository;
        this.s3Service = s3Service;
        this.lessonMapper = lessonMapper;
    }

    @Override
    public LessonDTO save(LessonDTO lessonDTO, MultipartFile videoFile) throws Exception {
        log.debug("Request to save Lesson: {}", lessonDTO);

        // Ánh xạ LessonDTO sang entity
        Lesson lesson = lessonMapper.toEntity(lessonDTO);

        // Lưu entity vào cơ sở dữ liệu
        Lesson savedLesson = lessonRepository.saveAndFlush(lesson);

        if (videoFile != null && !videoFile.isEmpty()) {
            try {
                String existingVideoUrl = savedLesson.getVideoUrl();
                if (existingVideoUrl != null) {
                    s3Service.deleteFile(existingVideoUrl);
                }
                String newVideoUrl = s3Service.uploadVideoToS3(videoFile);
                if (newVideoUrl != null) {
                    savedLesson.setVideoUrl(newVideoUrl);
                    savedLesson = lessonRepository.save(savedLesson);
                    LessonDTO result = lessonMapper.toDTO(savedLesson);
                    return result;
                } else {
                    // Xử lý trường hợp newVideoUrl là null, nếu cần
                    throw new RuntimeException("Lỗi: videoFile không được phép là null hoặc trống.");
                }
            } catch (Exception e) {
                throw new RuntimeException("Lỗi khi tải lên/cập nhật video lên S3: " + e.getMessage());
            }
        }

        return lessonMapper.toDTO(savedLesson);
    }


    @Override
    @Transactional(readOnly = true)
    public List<LessonDTO> getAllLessons() {
        return lessonRepository.findAll().stream().map(lessonMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LessonDTO> findOne(Long id) {
        return lessonRepository.findById(id).map(lessonMapper::toDTO);
    }


    @Override
    public void deleteLesson(Long id) {
        try {
            Optional<Lesson> lessonOptional = lessonRepository.findById(id);
            lessonOptional.ifPresent(lesson -> {
                // Xóa video từ S3
                s3Service.deleteFile(lesson.getVideoUrl());
                // Xóa bài học từ cơ sở dữ liệu
                lessonRepository.deleteById(id);
            });
        } catch (Exception e) {
            log.error("Failed to delete lesson", e);
            throw new RuntimeException("Failed to delete lesson: " + e.getMessage(), e);
        }
    }
}
