package com.fooddelivery.backend.controller;

import com.fooddelivery.backend.dto.CreateOrderRequest;
import com.fooddelivery.backend.dto.OrderResponse;
<<<<<<< HEAD
import com.fooddelivery.backend.model.Order;
import com.fooddelivery.backend.service.OrderService;
import lombok.RequiredArgsConstructor;

=======
import com.fooddelivery.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
>>>>>>> f5b49cb0862004dca76b729cf222dfe59f7d6ae0
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
<<<<<<< HEAD
        @Valid @RequestBody CreateOrderRequest request) {

    return ResponseEntity.ok(
            orderService.createOrder(
                    request.getTotalAmount(),
                    request.getUserId(),
                    request.getRestaurantId()
            )
    );
}

    @GetMapping
   public ResponseEntity<List<OrderResponse>> getAllOrders() {
    return ResponseEntity.ok(orderService.getAllOrders());
}

    @GetMapping("/{id}")
   public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
    return ResponseEntity.ok(orderService.getOrderById(id));
}
}
=======
            @Valid @RequestBody CreateOrderRequest request) {

        return ResponseEntity.ok(
                orderService.createOrder(
                        request.getTotalAmount(),
                        request.getUserId(),
                        request.getRestaurantId()
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}
>>>>>>> f5b49cb0862004dca76b729cf222dfe59f7d6ae0
