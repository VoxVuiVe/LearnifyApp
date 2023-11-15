package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.CategoryDTO;
import com.project.learnifyapp.dtos.SectionDTO;
import com.project.learnifyapp.models.Category;
import com.project.learnifyapp.models.Section;

import java.util.List;

public interface ISectionService {
    Section crateSection(SectionDTO sectionDTO);
    Section getSectionById(Long id);
    List<Section> getAllSections();
    Category updateSection(Long categoryId, CategoryDTO categoryDTO);
    void delete(Long id);
}
