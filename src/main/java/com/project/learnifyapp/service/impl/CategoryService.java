package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.CategoryDTO;
import com.project.learnifyapp.models.Category;
import com.project.learnifyapp.repository.CategoryReponsitory;
import com.project.learnifyapp.service.ICategoryService;
import com.project.learnifyapp.service.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryReponsitory categoryReponsitory;

    private final CategoryMapper categoryMapper;

    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category;
        if (categoryDTO.getId() != null) {
            category = categoryReponsitory.findById(categoryDTO.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
            category.setName(categoryDTO.getName());
            category.setIsDelete(categoryDTO.getIsDelete()); // Thêm dòng này
            if (categoryDTO.getParentId() != null && (category.getParent() == null || !category.getParent().getId().equals(categoryDTO.getParentId()))) {
                Category parent = categoryReponsitory.findById(categoryDTO.getParentId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent category not found"));
                category.setParent(parent);
            }
        } else {
            category = new Category();
            category.setName(categoryDTO.getName());
            category.setIsDelete(categoryDTO.getIsDelete()); // Thêm dòng này
            if (categoryDTO.getParentId() != null) {
                Category parent = categoryReponsitory.findById(categoryDTO.getParentId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent category not found"));
                category.setParent(parent);
            }
        }
        category = categoryReponsitory.save(category);
        return categoryMapper.toDTO(category);
    }

    @Override
    public Page<CategoryDTO> findAllPage(String keyword, PageRequest pageRequest) {
        if(keyword.equals("")) {
            keyword = null;
        }

        Page<Category> categoryPage = categoryReponsitory.searchCategory(keyword, pageRequest);
        Page<CategoryDTO> dtoPage = categoryPage.map(this::convertToDto);
        return dtoPage;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryReponsitory.findAll();
        return categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    private CategoryDTO convertToDto(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setIsDelete(category.getIsDelete());
        if (category.getParent() != null) {
            dto.setParentId(category.getParent().getId());
        }
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO findOne(Long id){
        Optional<Category> category = categoryReponsitory.findById(id);
        return category.map(categoryMapper::toDTO).orElse(null);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryReponsitory.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        if (!category.getCourses().isEmpty() || !category.getChildren().isEmpty()) {
            category.setIsDelete(false);
            categoryReponsitory.save(category);
        } else {
            categoryReponsitory.delete(category);
        }
    }

}
