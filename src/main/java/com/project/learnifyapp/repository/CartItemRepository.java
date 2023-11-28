package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem , Long> {
}
