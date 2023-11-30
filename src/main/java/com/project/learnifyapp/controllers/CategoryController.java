package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.CategoryDTO;
import com.project.learnifyapp.exceptions.BadRequestAlertException;
import com.project.learnifyapp.models.Category;
import com.project.learnifyapp.repository.CategoryReponsitory;
import com.project.learnifyapp.service.impl.CategoryService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("${api.prefix}/")
public class CategoryController {
    private final Logger log = LoggerFactory.getLogger(CategoryController.class);

    private static final String ENTITY_NAME = "categories";

    private final CategoryService categoryService;

    private final CategoryReponsitory categoryReponsitory;

    public CategoryController(CategoryService categoryService, CategoryReponsitory categoryReponsitory){
        this.categoryService = categoryService;
        this.categoryReponsitory = categoryReponsitory;
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        log.debug("REST request to save Category: {}", categoryDTO);
        if (categoryDTO.getParentId() != null){
            Optional<Category> parentCategory = categoryReponsitory.findById(categoryDTO.getParentId());
            parentCategory.isPresent();
        }
        CategoryDTO result = categoryService.save(categoryDTO);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody CategoryDTO categoryDTO){
        log.debug("REST request to update Category: {}", categoryDTO);
        if (categoryDTO.getId() == null){
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idNull");
        }
        if (!Objects.equals(id, categoryDTO.getId())){
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idInvalid");
        }
        if (!categoryReponsitory.existsById(id)){
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idNotFound");
        }
        CategoryDTO result = categoryService.save(categoryDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categoryDTOs = categoryService.findAll();
        return ResponseEntity.ok(categoryDTOs);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> getCategoriesById(@PathVariable Long id){
        CategoryDTO categoryDTO = categoryService.findOne(id);
        if (categoryDTO != null) {
            return ResponseEntity.ok(categoryDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
