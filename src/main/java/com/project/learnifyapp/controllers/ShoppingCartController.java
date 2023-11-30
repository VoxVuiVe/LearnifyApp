package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.CartItemDTO;
import com.project.learnifyapp.repository.CartItemRepository;
import com.project.learnifyapp.service.impl.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShoppingCartController {

    private static final String ENTITY_NAME = "shoppingCart";
    private final Logger log = LoggerFactory.getLogger(ShoppingCartController.class);
    private final ShoppingCartService shoppingCartService;
    private final CartItemRepository cartItemRepository;

    @PostMapping("/shoppingCarts")
    public ResponseEntity<List<CartItemDTO>> saveShoppingCart(@RequestBody CartItemDTO request) {
        log.debug("REST request to save cartItem: {}", request);
        List<CartItemDTO> cartItems = shoppingCartService.saveShoppingCart(request);
        return ResponseEntity.ok(cartItems);
    }

    @GetMapping("/shoppingCarts")
    public ResponseEntity<List<CartItemDTO>> findAll(@RequestParam Long userId) {
        List<CartItemDTO> cartItems = shoppingCartService.findAll(userId);
        return ResponseEntity.ok(cartItems);
    }

    @GetMapping("/shoppingCarts/{id}")
    public ResponseEntity<CartItemDTO> findOneCartItemById(@RequestParam Long cartItemId) {
        Optional<CartItemDTO> cartItem = shoppingCartService.findOneCartItemById(cartItemId);
        return cartItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CartItemDTO> deleteCartItem(@RequestParam Long cartItemId) {
        try {
            shoppingCartService.deleteCartItem(cartItemId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
