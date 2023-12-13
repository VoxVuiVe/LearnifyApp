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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final Logger log = LoggerFactory.getLogger(CategoryController.class);

    private static final String ENTITY_NAME = "categories";

    private final CategoryService categoryService;

    private final CategoryReponsitory categoryReponsitory;

    private final LocalizationUtils localizationUtils;

    @PostMapping("")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        log.debug("REST request to save Category: {}", categoryDTO);
        CategoryDTO result = categoryService.save(categoryDTO);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateCategoryResponse> updateCategory(@PathVariable("id") Long id,
                                                                 @Valid @RequestBody CategoryDTO categoryDTO, HttpServletRequest request){
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

    @GetMapping(value = "")
    public ResponseEntity<List<CategoryDTO>> getAllCate() {
        List<CategoryDTO> categoryDTOs = categoryService.findAll();
        return ResponseEntity.ok(categoryDTOs);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Map<String, Object>> getAllCategories(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CategoryDTO> categories = categoryService.findAllPage(keyword, pageRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("categories", categories.getContent());
        response.put("totalPages", categories.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoriesById(@PathVariable Long id){
        CategoryDTO categoryDTO = categoryService.findOne(id);
        if (categoryDTO != null) {
            return ResponseEntity.ok(categoryDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
