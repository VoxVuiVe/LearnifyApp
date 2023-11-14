package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
