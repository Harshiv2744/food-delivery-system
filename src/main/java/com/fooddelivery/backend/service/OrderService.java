package com.fooddelivery.backend.service;

import com.fooddelivery.backend.dto.OrderResponse;
import com.fooddelivery.backend.model.Order;
import com.fooddelivery.backend.model.OrderStatus;
import com.fooddelivery.backend.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public Page<OrderResponse> getOrdersByStatus(OrderStatus status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable)
                .map(this::mapToResponse);
    }

    public List<OrderResponse> getMyOrders(String email) {
        return orderRepository.findByUserEmail(email)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .total(order.getTotalAmount())  // FIXED
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .userEmail(order.getUser().getEmail())
                .build();
    }
}