package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.CategoryDTO;
import com.project.learnifyapp.dtos.SectionDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Category;
import com.project.learnifyapp.models.Section;

import java.util.List;

public interface ISectionService {
    SectionDTO save(SectionDTO sectionDTO) throws DataNotFoundException;
    SectionDTO getSection(Long id) throws DataNotFoundException;
    List<SectionDTO> getAllSections();

    SectionDTO update(Long id, SectionDTO sectionDTO) throws DataNotFoundException;

    SectionDTO remove(Long id) throws DataNotFoundException;


    boolean exitsById(Long id);
}
