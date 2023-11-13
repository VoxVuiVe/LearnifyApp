package com.project.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shopapp.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
    
}
