package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.CartItemDTO;
import com.project.learnifyapp.models.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CartMapper.class, UserMapper.class})
public interface CartItemMapper extends EntityMapper<CartItemDTO, CartItem>{

    @Mapping(source = "cart.id", target = "cartId")
    @Mapping(source = "course.id", target = "courseId")
    CartItemDTO toDTO (CartItem entity);

    @Mapping(source = "cartId", target = "cart.id")
    @Mapping(source = "courseId", target = "course.id")
    CartItem toEntity (CartItemDTO dto);
}
