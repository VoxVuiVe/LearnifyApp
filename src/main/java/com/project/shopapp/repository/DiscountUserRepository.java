package com.project.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shopapp.models.Discount;

public interface DiscountUserRepository extends JpaRepository<Discount, Long>{
    
}
