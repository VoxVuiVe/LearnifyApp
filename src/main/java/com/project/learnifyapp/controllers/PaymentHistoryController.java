package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.PaymentHistoryDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.PaymentHistory;
import com.project.learnifyapp.service.IPaymentHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/payment-history")
@RequiredArgsConstructor
public class PaymentHistoryController {
    private final IPaymentHistoryService paymentHistoryService;

    @PostMapping
    public ResponseEntity<PaymentHistory> createPaymentHistory(@RequestBody PaymentHistoryDTO paymentHistoryDTO)
            throws DataNotFoundException {
        PaymentHistory createdPaymentHistory = paymentHistoryService.createPaymentHistory(paymentHistoryDTO);
        return new ResponseEntity<>(createdPaymentHistory, HttpStatus.CREATED);
    }

    @GetMapping("/{paymentHistoryId}")
    public ResponseEntity<PaymentHistory> getPaymentHistoryById(@PathVariable Long paymentHistoryId)
            throws DataNotFoundException {
        PaymentHistory paymentHistory = paymentHistoryService.getPaymentHistoryById(paymentHistoryId);
        return new ResponseEntity<>(paymentHistory, HttpStatus.OK);
    }

    @PutMapping("/{paymentHistoryId}")
    public ResponseEntity<PaymentHistory> updatePaymentHistory(@PathVariable Long paymentHistoryId,
                                                               @RequestBody PaymentHistoryDTO updatedData)
            throws DataNotFoundException {
        PaymentHistory updatedPaymentHistory = paymentHistoryService.updatePaymentHistory(paymentHistoryId, updatedData);
        return new ResponseEntity<>(updatedPaymentHistory, HttpStatus.OK);
    }

    @DeleteMapping("/{paymentHistoryId}")
    public ResponseEntity<Void> deletePaymentHistory(@PathVariable Long paymentHistoryId)
            throws DataNotFoundException {
        paymentHistoryService.deletePaymentHistory(paymentHistoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
