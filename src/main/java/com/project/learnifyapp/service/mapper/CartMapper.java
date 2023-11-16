package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.CartDTO;
import com.project.learnifyapp.models.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses ={UserMapper.class,CourseCartMapper.class})
public interface CartMapper extends EntityMapper<CartDTO, Cart> {

    @Override
    @Mapping(source = "user.id", target = "userId")
    CartDTO toDTO(Cart entity);

    @Override
    @Mapping(source = "userId", target = "user.id")
    Cart toEntity(CartDTO dto);

}
