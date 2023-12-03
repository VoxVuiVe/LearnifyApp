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
@RequestMapping("${api.prefix}/shoppingCarts")
@RequiredArgsConstructor
public class ShoppingCartController {

    private static final String ENTITY_NAME = "shoppingCart";
    private final Logger log = LoggerFactory.getLogger(ShoppingCartController.class);
    private final ShoppingCartService shoppingCartService;
    private final CartItemRepository cartItemRepository;

    @PostMapping("")
    public ResponseEntity<List<CartItemDTO>> saveShoppingCart(@RequestBody CartItemDTO request) {
        log.debug("REST request to save cartItem: {}", request);
        List<CartItemDTO> cartItems = shoppingCartService.saveShoppingCart(request);
        return ResponseEntity.ok(cartItems);
    }

    @GetMapping("")
    public ResponseEntity<?> findAll(@RequestParam Long userId) {
        log.debug("REQUEST find all cart by userid: ", userId);
        List<CartItemDTO> cartItems = shoppingCartService.findAll(userId);
        return ResponseEntity.ok(cartItems);
    }

    @GetMapping("{id}")
    public ResponseEntity<CartItemDTO> findOneCartItemById(@RequestParam Long userId) {
        Optional<CartItemDTO> cartItem = shoppingCartService.findOneCartItemById(userId);
        return cartItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CartItemDTO> deleteCartItem(@RequestParam Long id) {
            shoppingCartService.deleteCartItem(id);
            return ResponseEntity.ok().build();
    }
}
