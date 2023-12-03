package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.CategoryDTO;
import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.models.Category;
import com.project.learnifyapp.repository.CategoryReponsitory;
import com.project.learnifyapp.service.ICategoryService;
import com.project.learnifyapp.service.mapper.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService implements ICategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryReponsitory categoryReponsitory;

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryReponsitory categoryReponsitory, CategoryMapper categoryMapper){
        this.categoryReponsitory = categoryReponsitory;
        this.categoryMapper = categoryMapper;
    }

    public CategoryDTO save(CategoryDTO categoryDTO){
        log.debug("Request to save category: {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryReponsitory.save(category);
        return categoryMapper.toDTO(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryReponsitory.findAll();
        return categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO findOne(Long id){
        Optional<Category> category = categoryReponsitory.findById(id);
        return category.map(categoryMapper::toDTO).orElse(null);
    }
}
