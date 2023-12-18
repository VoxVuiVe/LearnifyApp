package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.CartItemDTO;
import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.dtos.PaymentDTO;
import com.project.learnifyapp.models.CartItem;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.repository.CartItemRepository;
import com.project.learnifyapp.service.impl.ShoppingCartService;
import com.project.learnifyapp.service.mapper.CartItemMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("${api.prefix}/shoppingCarts")
@RequiredArgsConstructor
public class ShoppingCartController {

    private static final String ENTITY_NAME = "shoppingCart";
    private final Logger log = LoggerFactory.getLogger(ShoppingCartController.class);
    private final ShoppingCartService shoppingCartService;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    @PostMapping("/save")
    public ResponseEntity<CartItemDTO> saveShoppingCart(@RequestBody CartItemDTO request) {
        log.debug("REST request to save cartItem: {}", request);
        System.out.println(request);
        CartItemDTO cartItems = shoppingCartService.save(request);
        return ResponseEntity.ok(cartItems);
    }
//    @PutMapping("/update/")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllCartItemsByUserId(@PathVariable Long userId ) {
        try {
            List<CourseDTO> paymentList = shoppingCartService.findAllPage(userId);
            return new ResponseEntity<>(paymentList, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

//    @GetMapping("/user/{userId}")
//    public CartItem getUserIdStatus(@PathVariable Long userId){
//        return shoppingCartService.findByUserIdAndStatus(userId);
//    }
//    @GetMapping("/{userId}/cart")
//    public ResponseEntity<List<CourseDTO>> getAllCartItemsByUserId(
//            @PathVariable Long userId,
//            @RequestParam String cartData) {
//        try {
//            // Gọi service để lấy danh sách các khóa học từ giỏ hàng của người dùng
//            List<CourseDTO> cartCourses = shoppingCartService.getAllCartOfUser(cartData, userId);
//            return new ResponseEntity<>(cartCourses, HttpStatus.OK);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(Collections.emptyList());
//        }
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
//        shoppingCartService.deleteCartItem(id);
//        return ResponseEntity.ok().build();
//    }
}
