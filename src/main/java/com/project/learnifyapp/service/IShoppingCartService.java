package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.CartItemDTO;
import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.models.Course;

import java.util.List;
import java.util.Optional;

public interface IShoppingCartService {

//    List<CartItemDTO> saveShoppingCart(CartItemDTO request);

//    List<CartItemDTO> findAll(Long userId);

    CartItemDTO save(CartItemDTO cartItemDTO);

    Optional<CartItemDTO> findOneCartItemById(Long cartItemId);

//    Page<CartItemDTO> findAllPage(Long userId, PageRequest pageRequest);

    List<CourseDTO> findAllPage(Long userId);

//    void deleteCartItem(Long cartItemId);
}
