package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem , Long> {
    List<CartItem> findAllByUserId(Long userId);
//    CartItem findByUserIdAndCourseId(Long userId, Long courseId);

//    List<CartItem> findByCourseId(Long courseId);

}
