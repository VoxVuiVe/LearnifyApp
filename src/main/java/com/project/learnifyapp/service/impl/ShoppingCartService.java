package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.CartItemDTO;
import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.models.CartItem;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.User;
import com.project.learnifyapp.repository.CartItemRepository;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.repository.UserRepository;
import com.project.learnifyapp.service.IShoppingCartService;
import com.project.learnifyapp.service.mapper.CartItemMapper;
import com.project.learnifyapp.service.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartService implements IShoppingCartService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final CartItemRepository cartItemRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CartItemMapper cartItemMapper;
    private final CourseMapper courseMapper;
    private final CourseService courseService;

    @Override
    public CartItemDTO save(CartItemDTO cartItemDTO){
        System.out.println(cartItemDTO);
        CartItem cartItems = cartItemRepository.findCartItemByUser(cartItemDTO.getUserId());
        User user = userRepository.findById(cartItemDTO.getUserId()).orElse(null);
        if (cartItems == null){
            cartItems.setCartData(cartItemDTO.getCartData());
            cartItems.setStatus(cartItemDTO.getStatus());
            cartItems.setUser(user);
            cartItems.setTotalPrice(cartItemDTO.getTotalPrice());
            CartItem cartItemdto = cartItemRepository.save(cartItems);
            return cartItemMapper.toDTO(cartItemdto);
        }
        cartItems.setCartData(cartItemDTO.getCartData());
        cartItems.setStatus(cartItemDTO.getStatus());
        cartItems.setTotalPrice(cartItemDTO.getTotalPrice());
        cartItems.setUser(user);
        CartItem updatedCartItem = cartItemRepository.save(cartItems);
        return cartItemMapper.toDTO(updatedCartItem);
    }

//    public CartItem findByUserIdAndStatus(Long userId){
//        return cartItemRepository.findUserIdAndStatus(userId);
//    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartItemDTO> findOneCartItemById(Long userId) {
        return cartItemRepository.findById(userId).map(cartItemMapper::toDTO);
    }

    @Override
    public List<CourseDTO> findAllPage(Long userId) {
        List<CartItem> list = cartItemRepository.findAllByUserId(userId);
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for (CartItem cartItem : list) {
            String cartData = cartItem.getCartData();
            List<CourseDTO> course= getAllCartOfUser(cartData);
            courseDTOS.addAll(course);
        }
        if(courseDTOS.isEmpty()){
            log.debug("User not logged in");
            return Collections.emptyList();
        }
        return courseDTOS;
    }


    public List<CourseDTO> getAllCartOfUser(String cartData) {
        List<CourseDTO> courses = new ArrayList<>();

        if (cartData != null && !cartData.isEmpty()) {
            String[] courseIds = cartData.split(",");
            for (String courseId : courseIds) {
                try {
                    Long courseIdLong = Long.valueOf(courseId);
                    Optional<Course> optionalCourse = courseRepository.findById(courseIdLong);
//                    Course course = optionalCourse.orElse(null);

                    if (optionalCourse.isPresent()) {
                        Course course = optionalCourse.get();
                        CourseDTO courseDTO = courseMapper.toDTO(course);
                        courses.add(courseDTO);
                    }
                } catch (NumberFormatException e) {
                    log.error("Invalid courseId: {}", courseId);
                }
            }
        }
        return courses;
    }



}

