package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICategoryService {
    Page<CategoryDTO> findAllPage(String keyword, PageRequest pageRequest);

    @Transactional(readOnly = true)
    List<CategoryDTO> findAll();

    CategoryDTO findOne(Long id);

    void deleteCategory(Long id);
}
