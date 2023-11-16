package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.CategoryDTO;
import com.project.learnifyapp.models.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category>{
}
