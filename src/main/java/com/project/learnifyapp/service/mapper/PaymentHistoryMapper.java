package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.PaymentHistoryDTO;
import com.project.learnifyapp.models.PaymentHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PaymentMapper.class})
public interface PaymentHistoryMapper extends EntityMapper<PaymentHistoryDTO, PaymentHistory>{

    @Override
    @Mapping(source = "payment.id", target = "paymentId")
    PaymentHistoryDTO toDTO(PaymentHistory entity);

    @Override
    @Mapping(source = "paymentId", target = "payment.id")
    PaymentHistory toEntity(PaymentHistoryDTO dto);
}
