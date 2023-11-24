package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.CategoryDTO;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> findAll();

    CategoryDTO findOne(Long id);

}
