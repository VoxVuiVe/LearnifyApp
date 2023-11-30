package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.PaymentHistoryDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.PaymentHistory;

import java.util.List;

public interface IPaymentHistoryService {
    PaymentHistoryDTO createPaymentHistory(PaymentHistoryDTO paymentHistoryDTO) throws Exception;
    PaymentHistoryDTO getPaymentHistory(Long id) throws DataNotFoundException;
//    PaymentHistoryDTO updatePaymentHistory(Long id, PaymentHistoryDTO paymentHistoryDTO) throws DataNotFoundException;
    void deleteById(Long id);
    List<PaymentHistory> findByPaymentId(Long paymentId);
}
