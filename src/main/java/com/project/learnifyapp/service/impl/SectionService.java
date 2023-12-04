package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.SectionDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.Section;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.repository.SectionRepository;
import com.project.learnifyapp.service.ISectionService;
import com.project.learnifyapp.service.mapper.SectionMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    @Override
    public SectionDTO save(SectionDTO sectionDTO) throws DataNotFoundException {
        log.debug("Save new Section: {}", sectionDTO);

        Course course = courseRepository.findById(sectionDTO.getCourseId())
                .orElseThrow(() -> new DataNotFoundException("Course not found"));

        Section section = sectionMapper.toEntity(sectionDTO);
        section.setCourse(course);

        int newQuantityLesson = sectionDTO.getLesson().size();
        section.setQuantityLesson(newQuantityLesson);


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

        sectionRepository.delete(section);

        log.debug("Section remove: {}", section);

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
    public boolean exitsById(Long id) {
        return sectionRepository.existsById(id);
    }
}



