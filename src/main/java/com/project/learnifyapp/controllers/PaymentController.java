package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.PaymentDTO;
import com.project.learnifyapp.models.Payment;
import com.project.learnifyapp.service.impl.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("")
    public ResponseEntity<?> createPayment(@Valid @RequestBody PaymentDTO paymentDTO,
                                           BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            PaymentDTO paymentResponse = paymentService.createPayment(paymentDTO);
            return ResponseEntity.ok(paymentResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //get Payment theo userID
    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getPayments(@PathVariable("user_id") Long userId) {
        try {
            List<PaymentDTO> paymentList = paymentService.findByUserId(userId);
            return new ResponseEntity<>(paymentList, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPayment(@PathVariable("id") Long paymentId) {
        try {
            PaymentDTO existingPayment = paymentService.getPayment(paymentId);
            return ResponseEntity.ok(existingPayment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}") //Cong viec cua admin
    public ResponseEntity<?> updatePayment(@Valid @PathVariable Long id,
                                           @Valid @RequestBody PaymentDTO paymentDTO) {
        try {
            PaymentDTO payment = paymentService.updateOrder(id, paymentDTO);
            return ResponseEntity.ok().body(payment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable("id") Long id) {
        //Xoa mem -> cap nhat truong active = false
        paymentService.deletePayment(id);
        return ResponseEntity.ok("Payment deleted successfully!");
    }
}
