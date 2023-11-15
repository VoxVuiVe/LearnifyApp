package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.PaymentHistoryDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.PaymentHistory;

public interface IPaymentHistoryService {
    PaymentHistory createPaymentHistory(PaymentHistoryDTO paymentHistoryDTO) throws DataNotFoundException;
    PaymentHistory getPaymentHistoryById(Long paymentHistoryId) throws DataNotFoundException;
    PaymentHistory updatePaymentHistory(Long paymentHistoryId, PaymentHistoryDTO updatedData) throws DataNotFoundException;
    void deletePaymentHistory(Long paymentHistoryId);
}
