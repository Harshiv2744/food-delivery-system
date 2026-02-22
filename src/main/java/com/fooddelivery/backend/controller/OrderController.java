package com.fooddelivery.backend.controller;

import com.fooddelivery.backend.dto.CreateOrderRequest;
import com.fooddelivery.backend.dto.OrderResponse;
import com.fooddelivery.backend.enums.OrderStatus;
import com.fooddelivery.backend.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // ✅ USER can create order
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        OrderResponse response = orderService.createOrder(
                request.getTotalAmount(),
                request.getRestaurantId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ✅ USER & ADMIN can view orders
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // ✅ USER & ADMIN can view single order
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // 🔥 PART 5 – Only ADMIN can update status
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