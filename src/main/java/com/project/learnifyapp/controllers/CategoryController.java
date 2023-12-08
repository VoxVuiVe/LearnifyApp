package com.project.learnifyapp.controllers;

import com.project.learnifyapp.components.LocalizationUtils;
import com.project.learnifyapp.dtos.CategoryDTO;
import com.project.learnifyapp.exceptions.BadRequestAlertException;
import com.project.learnifyapp.repository.CategoryReponsitory;
import com.project.learnifyapp.responses.UpdateCategoryResponse;
import com.project.learnifyapp.service.impl.CategoryService;
import com.project.learnifyapp.utils.MessageKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("${api.prefix}/")
@RequiredArgsConstructor
public class CategoryController {
    private final Logger log = LoggerFactory.getLogger(CategoryController.class);

    private static final String ENTITY_NAME = "categories";

    private final CategoryService categoryService;

    private final CategoryReponsitory categoryReponsitory;

    private final LocalizationUtils localizationUtils;

    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        log.debug("REST request to save Category: {}", categoryDTO);
        CategoryDTO result = categoryService.save(categoryDTO);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<UpdateCategoryResponse> updateCategory(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody CategoryDTO categoryDTO,
                                                                 HttpServletRequest request){
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
        categoryService.save(categoryDTO);
        return ResponseEntity.ok(UpdateCategoryResponse.builder().message(localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_CATEGORY_SUCCESSFULLY)).build());
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

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
