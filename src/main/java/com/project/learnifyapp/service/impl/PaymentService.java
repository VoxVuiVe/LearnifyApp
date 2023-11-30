package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.PaymentDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Payment;
import com.project.learnifyapp.models.PaymentStatus;
import com.project.learnifyapp.models.User;
import com.project.learnifyapp.repository.PaymentRepository;
import com.project.learnifyapp.repository.UserRepository;
import com.project.learnifyapp.service.IPaymentService;
import com.project.learnifyapp.service.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {

    private final UserRepository userRepository;

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    private final ModelMapper modelMapper;

    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) throws Exception {
        User existingUser = userRepository.findById(paymentDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with ID: " + paymentDTO.getUserId()));

//        Payment payment = paymentMapper.toEntity(paymentDTO);
        //Tạo 1 luồng ánh xạ riêng để kiếm soát việc ánh xạ.
        modelMapper.typeMap(PaymentDTO.class, Payment.class).addMappings(mapper -> mapper.skip(Payment::setId));
        //Cập nhật các trường của thanh toán từ paymentDTO
//        Payment payment = new Payment();
//        modelMapper.map(paymentDTO, payment);
        Payment payment = paymentMapper.toEntity(paymentDTO);
        payment.setUser(existingUser);
        payment.setPaymentDate(new Date());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setActive(true);
        payment = paymentRepository.save(payment);
        return paymentMapper.toDTO(payment);
    }

    @Override
    public PaymentDTO getPayment(Long id) {
        Payment paymentResponse = paymentRepository.findById(id).orElse(null);
        return paymentMapper.toDTO(paymentResponse);
    }

    @Override
    public PaymentDTO updateOrder(Long id, PaymentDTO paymentDTO) throws DataNotFoundException {
        Payment existingPayment = paymentRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Cannot find payment with id: " + id));
        User existingUser = userRepository.findById(
                paymentDTO.getUserId()).orElseThrow(() ->
                new DataNotFoundException("Cannot find user with id: " + id));

        // Tạo một luồng bảng ánh xạ riêng để kiểm soát việc ánh xạ
        modelMapper.typeMap(PaymentDTO.class, Payment.class)
                .addMappings(mapper -> mapper.skip(Payment::setId));

        modelMapper.map(paymentDTO, existingPayment);
        existingPayment.setUser(existingUser);
        existingPayment = paymentRepository.save(existingPayment);
        return paymentMapper.toDTO(existingPayment);
    }

    @Override
    public void deletePayment(Long id) {
        Payment existingPayment = paymentRepository.findById(id).orElse(null);
        if(existingPayment != null) {
            existingPayment.setActive(false);
            paymentRepository.save(existingPayment);
        }
    }

    @Override
    public List<PaymentDTO> findByUserId(Long userId) {
        List<Payment> list = paymentRepository.findByUserId(userId);
        return paymentMapper.toDTO(list);
    }
}
