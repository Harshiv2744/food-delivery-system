package com.fooddelivery.backend.controller;

import com.fooddelivery.backend.dto.OrderResponse;
import com.fooddelivery.backend.enums.OrderStatus;
import com.fooddelivery.backend.service.OrderService;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<Page<OrderResponse>> filterByStatus(
            @RequestParam OrderStatus status,
            Pageable pageable) {

        return ResponseEntity.ok(orderService.getOrdersByStatus(status, pageable));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponse>> getMyOrders(Authentication authentication) {
        return ResponseEntity.ok(orderService.getMyOrders(authentication.getName()));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/pay")
    public ResponseEntity<OrderResponse> pay(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.processPayment(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {

        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
}