package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.LessonDTO;
import com.project.learnifyapp.models.Lesson;
import com.project.learnifyapp.models.Section;
import com.project.learnifyapp.repository.LessonRepository;
import com.project.learnifyapp.repository.SectionRepository;
import com.project.learnifyapp.service.ILessonService;
import com.project.learnifyapp.service.S3Service;
import com.project.learnifyapp.service.mapper.LessonMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonService implements ILessonService {

    private final Logger log = LoggerFactory.getLogger(LessonService.class);
    private final S3Service s3Service;
    private final LessonRepository lessonRepository;
    private final SectionRepository sectionRepository;
    private final LessonMapper lessonMapper;


    @Override
    public LessonDTO save(LessonDTO lessonDTO, MultipartFile videoFile) throws Exception {
        log.debug("Request to save Lesson: {}", lessonDTO);

        // Ánh xạ LessonDTO sang entity
        Lesson lesson = lessonMapper.toEntity(lessonDTO);

        // Lưu entity vào cơ sở dữ liệu
        Lesson savedLesson = lessonRepository.saveAndFlush(lesson);

        Section section = lessonRepository.findByIdWithSection(lesson.getId());

        // Kiểm tra nullability của Section
        if (section != null && lesson.getId() != null) {
            // Tăng quantity_lesson của Section khi thêm Lesson
            section.setQuantityLesson(section.getQuantityLesson() + 1);
        } else {
            throw new RuntimeException("Section không được phép là null.");
        }

        // Tăng total_minutes của Section khi thêm Lesson hoặc cập nhật
        section.setTotalMinutes(section.getTotalMinutes() + savedLesson.getTime());

        // Lưu Section sau khi cập nhật
        sectionRepository.save(section);

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
    public Page<LessonDTO> findAllPage(String keyword, PageRequest pageRequest){
        if (keyword.equals("")){
            keyword = null;
        }
        Page<Lesson> lessonPage = lessonRepository.searchLesson(keyword, pageRequest);
        Page<LessonDTO> dtoPage = lessonPage.map(this::convertToDto);
        return dtoPage;
    }

    private LessonDTO convertToDto(Lesson lesson){
        LessonDTO dto = new LessonDTO();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setTime(lesson.getTime());
        dto.setVideoUrl(lesson.getVideoUrl());
        dto.setQuestionAndAnswer(lesson.getQuestionAndAnswer());
        dto.setOverview(lesson.getOverview());
        dto.setNote(lesson.getNote());
        dto.setComment(lesson.getComment());
        if (lesson.getSection() != null){
            dto.setSectionId(lesson.getSection().getId());
        }
        return dto;
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<LessonDTO> findOne(Long id) {
        return lessonRepository.findById(id).map(lessonMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LessonDTO> findOneWithPresignedURL(Long id) {
        Optional<LessonDTO> lessonDTO = lessonRepository.findById(id).map(lessonMapper::toDTO);
        if (lessonDTO.isPresent()) {
            LessonDTO lesson = lessonDTO.get();
            String videoKey = lesson.getVideoUrl(); // Giả định rằng getVideoUrl trả về key

            // Kiểm tra nullability của videoKey
            if (videoKey != null) {
                // Sử dụng S3Service để lấy presigned URL
                String presignedURL = s3Service.getPresignedURL(videoKey);

                // Cập nhật đường dẫn video trong lessonDTO với presigned URL
                lesson.setVideoUrl(presignedURL);

                return Optional.of(lesson);
            } else {
                // Xử lý trường hợp videoKey là null, nếu cần
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }


    @Override
    public void deleteLesson(Long id) {
        try {
            Optional<Lesson> lessonOptional = lessonRepository.findById(id);
            lessonOptional.ifPresent(lesson -> {
                // Lấy section từ lesson
                Section section = lesson.getSection();

                // Cập nhật quantity_lesson và total_time trong section
                section.setQuantityLesson(section.getQuantityLesson() - 1);
                section.setTotalMinutes(section.getTotalMinutes() - lesson.getTime());

                // Lưu section
                sectionRepository.save(section);

                s3Service.deleteFile(lesson.getVideoUrl());
                lessonRepository.deleteById(id);
            });
        } catch (Exception e) {
            log.error("Failed to delete lesson", e);
            throw new RuntimeException("Failed to delete lesson: " + e.getMessage(), e);
        }
    }

    @Transactional(readOnly = true)
    public boolean isLessonExists(Long lessonId) {
        return lessonRepository.existsById(lessonId);
    }
}
