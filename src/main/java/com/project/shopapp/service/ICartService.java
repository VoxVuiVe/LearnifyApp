package com.project.shopapp.service;

import com.project.shopapp.dtos.CartDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Cart;

public interface ICartService {

    Cart createCart(CartDTO cartDTO) throws DataNotFoundException;

    Cart getCartById(Long cartId) throws DataNotFoundException;

    Cart updateCart(Long cartId, CartDTO updatedData) throws DataNotFoundException;
    
    void deleteCart(Long cartId) throws DataNotFoundException;
}
