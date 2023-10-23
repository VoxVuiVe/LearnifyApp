package com.project.shopapp.service;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.dtos.SectionDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Section;

import java.util.List;

public interface ISectionCategory {
    Section crateSection(SectionDTO sectionDTO);
    Section getSectionById(Long id);
    List<Section> getAllSections();
    Category updateSection(Long categoryId, CategoryDTO categoryDTO);
    void delete(Long id);
}
