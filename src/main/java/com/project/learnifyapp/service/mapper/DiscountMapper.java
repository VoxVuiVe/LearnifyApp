package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.DiscountDTO;
import com.project.learnifyapp.models.Discount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface DiscountMapper extends EntityMapper<DiscountDTO, Discount> {
}
