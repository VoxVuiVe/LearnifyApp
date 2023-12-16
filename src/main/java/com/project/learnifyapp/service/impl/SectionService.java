package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.LessonDTO;
import com.project.learnifyapp.dtos.SectionDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.Lesson;
import com.project.learnifyapp.models.Section;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.repository.LessonRepository;
import com.project.learnifyapp.repository.SectionRepository;
import com.project.learnifyapp.service.ISectionService;
import com.project.learnifyapp.service.mapper.SectionMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SectionService implements ISectionService {

    private final Logger log = LoggerFactory.getLogger(CommentsService.class);

    private final SectionRepository sectionRepository;

    private final SectionMapper sectionMapper;

    private final CourseRepository courseRepository;

    private  final LessonRepository lessonRepository;


    @Override
    public SectionDTO save(SectionDTO sectionDTO) throws DataNotFoundException {
        log.debug("Save new Section: {}", sectionDTO);

        Course course = courseRepository.findById(sectionDTO.getCourseId())
                .orElseThrow(() -> new DataNotFoundException("Course not found"));

        Section section = sectionMapper.toEntity(sectionDTO);
        section.setQuantityLesson(0);
        section.setTotalMinutes(0);
        section.setCourse(course);

        Section saveSection = sectionRepository.save(section);

        log.debug("New section saved: {}", saveSection);

        return sectionMapper.toDTO(saveSection);
    }

    @Override
    public SectionDTO update(Long id, SectionDTO sectionDTO) throws DataNotFoundException {
        log.debug("Update section with ID: {}", id);

        Section exitingSection = sectionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Section not found"));

        Course course = courseRepository.findById(sectionDTO.getCourseId())
                .orElseThrow(() -> new DataNotFoundException("Course not found"));

        sectionMapper.updateSectionFromDTO(sectionDTO, exitingSection);
        exitingSection.setCourse(course);

        Section updateSection = sectionRepository.save(exitingSection);

        log.debug("Update section: {}", updateSection);

        return sectionMapper.toDTO(updateSection);
    }


    @Override
    public SectionDTO remove(Long id) throws DataNotFoundException {
        log.debug("Remove section with ID: {}", id);

        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Section not found"));

        Lesson sectionId =  lessonRepository.findBySectionId(id);

        if(sectionId != null){
            sectionRepository.updateIsDeleteById(id,false);
            return null;
        }

        sectionRepository.delete(section);
        return sectionMapper.toDTO(section);
    }


    @Override
    public SectionDTO getSection(Long id) throws DataNotFoundException {
        log.debug("Fetching section with ID: {}", id);

        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Section not found"));

        log.debug("Fetched rating: {}", section);

        return sectionMapper.toDTO(section);
    }

    @Override
    public List<SectionDTO> getAllSections() {
        return sectionRepository.findAll().stream().map(sectionMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Page<SectionDTO> findAllPage(String keyword, PageRequest pageRequest) {
        if(keyword.equals("")) {
            keyword = null;
        }
        Page<Section> discountPage = sectionRepository.searchSection(keyword,pageRequest);
        Page<SectionDTO> dtoPage = discountPage.map(this::convertToDto);
        return dtoPage;
    }

    private SectionDTO convertToDto(Section section) {
        SectionDTO dto = new SectionDTO();
        dto.setId(section.getId());
        dto.setTitle(section.getTitle());
        dto.setQuantityLesson(section.getQuantityLesson());
        dto.setTotalMinutes(section.getTotalMinutes());
        dto.setResource(section.getResource());
        dto.setIsDelete(section.getIsDelete());
        dto.setCourseId(section.getCourse().getId());
        return dto;
    }

    @Override
    public boolean exitsById(Long id) {
        return sectionRepository.existsById(id);
    }
}



