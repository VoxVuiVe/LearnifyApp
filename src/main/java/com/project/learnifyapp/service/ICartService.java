package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.CartDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Cart;

public interface ICartService {

    Cart createCart(CartDTO cartDTO) throws DataNotFoundException;

    Cart getCartById(Long cartId) throws DataNotFoundException;

    Cart updateCart(Long cartId, CartDTO updatedData) throws DataNotFoundException;
    
    void deleteCart(Long cartId) throws DataNotFoundException;
}
