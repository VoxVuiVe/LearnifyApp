package com.project.shopapp.service.impl;

import com.project.shopapp.dtos.CartDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Cart;
import com.project.shopapp.models.Course;
import com.project.shopapp.models.Payment;
import com.project.shopapp.models.User;
import com.project.shopapp.repository.CartRepository;
import com.project.shopapp.repository.CourseRepository;
import com.project.shopapp.repository.UserRepository;
import com.project.shopapp.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public Cart createCart(CartDTO cartDTO) throws DataNotFoundException {
        Long courseId = cartDTO.getCourseId();
        Long userId = cartDTO.getUserId();

        // Kiểm tra sự tồn tại của Course và User
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new DataNotFoundException("Course không tồn tại"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User không tồn tại"));

        // Convert CartDTO -> Cart
        Cart newCart = Cart.builder()
                .createDate(LocalDateTime.now())
                .name(cartDTO.getName())
                .quantity(cartDTO.getQuantity())
                .price(cartDTO.getPrice())
                .course(course)
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
    public Cart updateCart(Long cartId, CartDTO updatedData) throws DataNotFoundException {
        Cart cart = getCartById(cartId);

        // Update the cart fields based on the updatedData
        cart.setName(updatedData.getName());
        cart.setQuantity(updatedData.getQuantity());
        cart.setPrice(updatedData.getPrice());

        return cartRepository.save(cart);
    }

    @Override
    public void deleteCart(Long cartId) throws DataNotFoundException {
        Cart cart = getCartById(cartId);
        cartRepository.delete(cart);
    }
}
