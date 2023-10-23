package com.project.shopapp.repository;

import com.project.shopapp.models.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
}
