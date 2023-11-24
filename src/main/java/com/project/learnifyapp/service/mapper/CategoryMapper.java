package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.CategoryDTO;
import com.project.learnifyapp.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
    @Mappings({@Mapping(source = "parent.id", target = "parentId")})
    CategoryDTO toDTO(Category category);

    @Mappings({@Mapping(source = "parentId", target = "parent.id")})
    Category toEntity(CategoryDTO categoryDTO);
}
