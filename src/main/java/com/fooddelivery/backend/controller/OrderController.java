package com.fooddelivery.backend.controller;

import com.fooddelivery.backend.dto.OrderResponse;
import com.fooddelivery.backend.service.OrderService;
import com.fooddelivery.backend.enums.OrderStatus;  // ✅ IMPORTANT IMPORT

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // ============================================
    // GET ALL ORDERS (ADMIN)
    // ============================================
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    // ============================================
    // FILTER ORDERS BY STATUS (ADMIN)
    // ============================================
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<Page<OrderResponse>> filterByStatus(
            @RequestParam OrderStatus status,
            Pageable pageable) {

        return ResponseEntity.ok(
                orderService.getOrdersByStatus(status, pageable)
        );
    }

    // ============================================
    // GET LOGGED-IN USER ORDERS
    // ============================================
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponse>> getMyOrders(Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(orderService.getMyOrders(email));
    }

    // ============================================
    // USER PAYMENT
    // ============================================
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/pay")
    public ResponseEntity<OrderResponse> makePayment(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.processPayment(id));
    }

    // ============================================
    // UPDATE ORDER STATUS (ADMIN)
    // ============================================
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {

        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
}