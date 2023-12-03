package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.CartItemDTO;
import com.project.learnifyapp.models.CartItem;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.repository.CartItemRepository;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.repository.UserRepository;
import com.project.learnifyapp.service.IShoppingCartService;
import com.project.learnifyapp.service.mapper.CartItemMapper;
import com.project.learnifyapp.service.mapper.CourseMapper;
import com.project.learnifyapp.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ShoppingCartService implements IShoppingCartService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final CartItemRepository cartItemRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CartItemMapper cartItemMapper;
    private final CourseMapper courseMapper;
    private final UserMapper userMapper;

    @Override
    public List<CartItemDTO> saveShoppingCart(CartItemDTO request) {
        if (request.getUserId() == null) {
            log.debug("User not logged in");
            return Collections.emptyList();
        }

        // Get cart data from session or localStorage
        String cartData = request.getCartData();
        if (cartData == null) {
            log.debug("No cart data provided");
            return Collections.emptyList();
        }

        String[] courses = cartData.split(",");
        List<Course> listCourse = new ArrayList<>();

        for (String courseId : courses) {
            try {
                Long courseIdLong = Long.valueOf(courseId);
                Course courseDTO = courseRepository.findById(courseIdLong).orElse(null);
                if (courseDTO != null) {
                    listCourse.add(courseDTO);
                }
            } catch (NumberFormatException e) {
                log.error("Invalid courseId: {}", courseId);
            }
        }

        List<CartItemDTO> newCartItem = new ArrayList<>();

        for (Course course : listCourse) {
            // Check if the course already exists in the cart
            CartItem existingCartItem = cartItemRepository.findByUserIdAndCourseId(request.getUserId(), course.getId());
            if (existingCartItem != null) {
                log.info("Course {} already exists in the cart", course.getId());
                continue;
            }

            CartItemDTO cartItemDTO = CartItemDTO.builder()
                    .totalPrice(course.getPrice())
                    .courseId(String.valueOf(course.getId()))
                    .userId(request.getUserId())
                    .build();

            CartItem ci = cartItemMapper.toEntity(cartItemDTO);
            newCartItem.add(cartItemMapper.toDTO(cartItemRepository.save(ci)));
        }

        return newCartItem;
    }


    @Override
    @Transactional(readOnly = true)
    public List<CartItemDTO> findAll(Long userId){
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);
        return cartItemMapper.toDTO(cartItems);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartItemDTO> findOneCartItemById(Long userId) {
        return cartItemRepository.findById(userId).map(cartItemMapper::toDTO);
    }

    @Override
    public void deleteCartItem(Long id) {
        try {
            cartItemRepository.deleteById(id);
        } catch (Exception e) {
            log.debug("Failed to delete cartItem", e);
            throw new RuntimeException("Failed to delete cartItem: " + e.getMessage(), e);
        }
    }
}

