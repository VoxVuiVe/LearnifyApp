package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
}
