package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.CartItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IShoppingCartService {

    List<CartItemDTO> saveShoppingCart(CartItemDTO request);

//    List<CartItemDTO> findAll(Long userId);

    Optional<CartItemDTO> findOneCartItemById(Long cartItemId);

    Page<CartItemDTO> findAllPage(Long userId, PageRequest pageRequest);

    void deleteCartItem(Long cartItemId);

}
