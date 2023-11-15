package com.project.learnifyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.learnifyapp.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
    
}
