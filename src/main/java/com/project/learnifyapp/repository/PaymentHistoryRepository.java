package com.project.learnifyapp.repository;

import com.project.learnifyapp.dtos.PaymentHistoryDTO;
import com.project.learnifyapp.models.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
    List<PaymentHistory> findByPaymentId(Long id);
}
