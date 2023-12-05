package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.PaymentDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Payment;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IPaymentService {
    PaymentDTO createPayment(PaymentDTO paymentDTO) throws Exception;

    int orderReturn(HttpServletRequest request);

    PaymentDTO getPayment(Long id);
    PaymentDTO updateOrder(Long id, PaymentDTO paymentDTO) throws DataNotFoundException;
    void deletePayment(Long id);
    List<PaymentDTO> findByUserId(Long userId);
}
