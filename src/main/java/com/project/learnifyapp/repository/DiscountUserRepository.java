package com.project.learnifyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.learnifyapp.models.Discount;

public interface DiscountUserRepository extends JpaRepository<Discount, Long>{
    
}
