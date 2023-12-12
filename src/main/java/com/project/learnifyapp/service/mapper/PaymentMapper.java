package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.PaymentDTO;
import com.project.learnifyapp.models.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CourseMapper.class})
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment>{
    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cartItem.id", target = "cartItemId")
    PaymentDTO toDTO(Payment entity);

    @Override
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "cartItemId", target = "cartItem.id")
    Payment toEntity(PaymentDTO dto);
}
