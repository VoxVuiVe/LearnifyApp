package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.CartDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Cart;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.CourseCart;
import com.project.learnifyapp.models.User;
import com.project.learnifyapp.repository.CartRepository;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.repository.UserRepository;
import com.project.learnifyapp.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public Cart createCart(CartDTO cartDTO) throws DataNotFoundException {
        // Kiểm tra sự tồn tại của User
        User user = userRepository.findById(cartDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User không tồn tại"));

        // Convert CartDTO -> Cart
        Cart newCart = Cart.builder()
                .quantity(cartDTO.getQuantity())
                .totalMoney(cartDTO.getTotalMoney())
                .user(user)
                .build();

        return cartRepository.save(newCart);
    }

    @Override
    public Cart getCartById(Long cartId) throws DataNotFoundException {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new DataNotFoundException("Cart không tồn tại!"));
    }

    @Override
    public Cart updateCart(Long cartId, CartDTO cartDTO) throws DataNotFoundException {
        Cart cart = getCartById(cartId);

        // Update the cart fields based on the updatedData
        cart.setQuantity(cartDTO.getQuantity());
        cart.setTotalMoney(cartDTO.getTotalMoney());

        // Assuming you have a method to get User by userId
        User user = userRepository.getOne(cartDTO.getUserId());
        cart.setUser(user);

        return cartRepository.save(cart);
    }


    @Override
    public void deleteCart(Long cartId) throws DataNotFoundException {
        Cart cart = getCartById(cartId);
        cartRepository.delete(cart);
    }
}
