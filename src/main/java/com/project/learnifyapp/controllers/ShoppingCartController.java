package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.CartItemDTO;
import com.project.learnifyapp.repository.CartItemRepository;
import com.project.learnifyapp.service.impl.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/{userId}")
    public ResponseEntity<Page<CartItemDTO>> getAllCartItemsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CartItemDTO> pageResult = shoppingCartService.findAllPage(userId, pageRequest);
        return ResponseEntity.ok().body(pageResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        shoppingCartService.deleteCartItem(id);
        return ResponseEntity.ok().build();
    }

}
