package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.DiscountDTO;
import com.project.learnifyapp.models.Discount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface DiscountMapper extends EntityMapper<DiscountDTO, Discount> {
}
