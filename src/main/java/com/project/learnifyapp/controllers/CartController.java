package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.CartDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Cart;
import com.project.learnifyapp.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {
    
    private final ICartService cartService;

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody CartDTO cartDTO) throws DataNotFoundException {
        Cart createdCart = cartService.createCart(cartDTO);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long cartId) throws DataNotFoundException {
        Cart cart = cartService.getCartById(cartId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long cartId, @RequestBody CartDTO updatedData)
            throws DataNotFoundException {
        Cart updatedCart = cartService.updateCart(cartId, updatedData);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) throws DataNotFoundException {
        cartService.deleteCart(cartId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
