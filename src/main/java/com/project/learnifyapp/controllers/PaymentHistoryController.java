package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.PaymentHistoryDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.PaymentHistory;
import com.project.learnifyapp.responses.PaymentHistoryResponse;
import com.project.learnifyapp.service.IPaymentHistoryService;
import com.project.learnifyapp.service.impl.PaymentHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/payment_history")
@RequiredArgsConstructor
public class PaymentHistoryController {
    private final PaymentHistoryService paymentHistoryService;

    @PostMapping("")
    public ResponseEntity<?> createPaymentHistory(@Valid @RequestBody PaymentHistoryDTO paymentHistoryDTO,
                                                  BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            PaymentHistoryDTO newPaymentHistory = paymentHistoryService.createPaymentHistory(paymentHistoryDTO);
            return ResponseEntity.ok().body(newPaymentHistory);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentHistory(@PathVariable("id") Long id) throws DataNotFoundException{
        PaymentHistoryDTO paymentHistory = paymentHistoryService.getPaymentHistory(id);
        return ResponseEntity.status(HttpStatus.OK).body(paymentHistory);
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<?> getPaymentHistories(@PathVariable("paymentId") Long paymentId) {
        List<PaymentHistory> paymentHistories = paymentHistoryService.findByPaymentId(paymentId);
        List<PaymentHistoryResponse> paymentHistoryResponses = paymentHistories.stream().map(PaymentHistoryResponse::fromPaymentHistory).toList();
        return ResponseEntity.ok(paymentHistoryResponses);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> updatePaymentHistory(@Valid @PathVariable Long id, @RequestBody PaymentHistoryDTO paymentHistoryDTO) {
//        PaymentHistory paymentHistory = paymentHistoryService.up
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaymentHistory(
            @Valid @PathVariable("id") Long id) {
        paymentHistoryService.deleteById(id);
        return ResponseEntity.ok().body("Delete Order detail with id : " + id + " successfully");
        //return ResponseEntity.noContent().build();
    }
}
