package com.project.learnifyapp.repository;

import com.project.learnifyapp.dtos.PaymentHistoryDTO;
import com.project.learnifyapp.models.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
    List<PaymentHistory> findByPaymentId(Long id);
}
