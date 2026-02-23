package com.fooddelivery.backend.controller;

import com.fooddelivery.backend.dto.CartItemRequest;
import com.fooddelivery.backend.dto.CartResponse;
import com.fooddelivery.backend.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CUSTOMER')")
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addItem(
            Authentication authentication,
            @Valid @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addItem(authentication.getName(), request));
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponse> updateQuantity(
            Authentication authentication,
            @PathVariable Long cartItemId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateItemQuantity(authentication.getName(), cartItemId, quantity));
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponse> removeItem(
            Authentication authentication,
            @PathVariable Long cartItemId) {
        return ResponseEntity.ok(cartService.removeItem(authentication.getName(), cartItemId));
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(Authentication authentication) {
        return ResponseEntity.ok(cartService.getCart(authentication.getName()));
    }
}
