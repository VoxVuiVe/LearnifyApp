package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.DiscountDTO;
import com.project.learnifyapp.models.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DiscountCourseMapper.class})
public interface DiscountMapper extends EntityMapper<DiscountDTO, Discount> {
}
