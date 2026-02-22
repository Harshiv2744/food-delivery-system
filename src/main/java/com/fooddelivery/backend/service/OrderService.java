package com.fooddelivery.backend.service;

import com.fooddelivery.backend.dto.OrderResponse;
import com.fooddelivery.backend.model.Order;
import com.fooddelivery.backend.model.OrderStatus;
import com.fooddelivery.backend.repository.OrderRepository;
import com.fooddelivery.backend.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    // 🔹 Admin - Paginated Orders
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    // 🔹 Admin - Filter by Status
    public Page<OrderResponse> getOrdersByStatus(OrderStatus status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable)
                .map(this::mapToResponse);
    }

    // 🔹 User - My Orders
    public List<OrderResponse> getMyOrders(String email) {
        return orderRepository.findByUserEmail(email)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 🔹 Update Order Status (Admin)
    public OrderResponse updateOrderStatus(Long id, OrderStatus newStatus) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        OrderStatus current = order.getStatus();

        if (current == OrderStatus.DELIVERED || current == OrderStatus.CANCELLED) {
            throw new RuntimeException("Completed order cannot be modified");
        }

        if (current == OrderStatus.PENDING && newStatus == OrderStatus.OUT_FOR_DELIVERY) {
            throw new RuntimeException("Invalid status transition");
        }

        order.setStatus(newStatus);

        return mapToResponse(orderRepository.save(order));
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .total(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .userEmail(order.getUser().getEmail())
                .build();
    }
}