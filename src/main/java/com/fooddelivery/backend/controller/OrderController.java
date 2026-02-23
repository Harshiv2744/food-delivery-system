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

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponse>> getMyOrders(Authentication authentication) {
        return ResponseEntity.ok(orderService.getMyOrders(authentication.getName()));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(
            Authentication authentication,
            @RequestParam Long restaurantId) {
        return ResponseEntity.ok(orderService.placeOrderFromCart(authentication.getName(), restaurantId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER') or hasRole('RESTAURANT') or hasRole('DELIVERY_AGENT')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PreAuthorize("hasRole('RESTAURANT')")
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(orderService.getOrdersByRestaurantId(restaurantId));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/{id}/pay")
    public ResponseEntity<OrderResponse> pay(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.processPayment(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('RESTAURANT')")
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
}