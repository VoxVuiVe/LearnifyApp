package com.project.learnifyapp.repository;

import com.project.learnifyapp.dtos.PaymentDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.learnifyapp.models.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{
    List<Payment> findByUserId(Long userId);

}
