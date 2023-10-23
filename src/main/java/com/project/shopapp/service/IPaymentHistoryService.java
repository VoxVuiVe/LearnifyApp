package com.project.shopapp.service;

import com.project.shopapp.dtos.PaymentHistoryDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.PaymentHistory;

public interface IPaymentHistoryService {
    PaymentHistory createPaymentHistory(PaymentHistoryDTO paymentHistoryDTO) throws DataNotFoundException;
    PaymentHistory getPaymentHistoryById(Long paymentHistoryId) throws DataNotFoundException;
    PaymentHistory updatePaymentHistory(Long paymentHistoryId, PaymentHistoryDTO updatedData) throws DataNotFoundException;
    void deletePaymentHistory(Long paymentHistoryId);
}
