package com.fooddelivery.backend.controller;

import com.fooddelivery.backend.dto.CreateOrderRequest;
import com.fooddelivery.backend.dto.OrderResponse;
import com.fooddelivery.backend.enums.OrderStatus;   // ✅ IMPORTANT IMPORT
import com.fooddelivery.backend.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.security.access.prepost.PreAuthorize;
=======
import org.springframework.http.HttpStatus;
>>>>>>> dfc5c9c (Day 6 - completed)
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 🔐 Only USER can create orders
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

<<<<<<< HEAD
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(
                        request.getTotalAmount(),
                        request.getRestaurantId()
                ));
=======
        OrderResponse response = orderService.createOrder(
                request.getTotalAmount(),
                request.getUserId(),
                request.getRestaurantId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
>>>>>>> dfc5c9c (Day 6 - completed)
    }

    // 🔐 USER & ADMIN can view orders (filtered in service)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // 🔐 USER & ADMIN can view specific order
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // 🔐 ADMIN can update order status
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {

        return ResponseEntity.ok(
                orderService.updateOrderStatus(id, status)
        );
    }
}