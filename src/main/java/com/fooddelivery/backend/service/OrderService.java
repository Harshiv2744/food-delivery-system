package com.fooddelivery.backend.service;

import com.fooddelivery.backend.dto.OrderItemResponse;
import com.fooddelivery.backend.dto.OrderResponse;
import com.fooddelivery.backend.enums.OrderStatus;
import com.fooddelivery.backend.enums.PaymentStatus;
import com.fooddelivery.backend.exception.ResourceNotFoundException;
import com.fooddelivery.backend.model.Order;
import com.fooddelivery.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

<<<<<<< HEAD
    // ✅ GET ALL ORDERS (ADMIN)
=======
    // ✅ GET ALL ORDERS
>>>>>>> 8c577e4e324fbcf8576365bd773e212da08a34c7
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

<<<<<<< HEAD
    // ✅ FILTER BY STATUS (ADMIN)
=======
    // ✅ FILTER BY STATUS
>>>>>>> 8c577e4e324fbcf8576365bd773e212da08a34c7
    public Page<OrderResponse> getOrdersByStatus(OrderStatus status, Pageable pageable) {
        return orderRepository.findByOrderStatus(status, pageable)
                .map(this::mapToResponse);
    }

<<<<<<< HEAD
    // ✅ GET MY ORDERS (USER)
=======
    // ✅ GET MY ORDERS
>>>>>>> 8c577e4e324fbcf8576365bd773e212da08a34c7
    public List<OrderResponse> getMyOrders(String email) {
        return orderRepository.findByUserEmail(email)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ PROCESS PAYMENT
    public OrderResponse processPayment(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
<<<<<<< HEAD
                        new ResourceNotFoundException("Order", "id", orderId));

        // 🔥 FIXED HERE
        order.setPaymentStatus(PaymentStatus.SUCCESS);
        order.setOrderStatus(OrderStatus.CONFIRMED);

        orderRepository.save(order);

        return mapToResponse(order);
    }

    // ✅ UPDATE ORDER STATUS (ADMIN)
=======
                        new ResourceNotFoundException("Order not found with id " + orderId));

        // Prevent double payment (Good practice ⭐)
        if (order.getPaymentStatus() == PaymentStatus.COMPLETED) {
            throw new RuntimeException("Payment already completed for this order");
        }

        order.setPaymentStatus(PaymentStatus.COMPLETED);
        order.setOrderStatus(OrderStatus.CONFIRMED);

        return mapToResponse(orderRepository.save(order));
    }

    // ✅ UPDATE ORDER STATUS
>>>>>>> 8c577e4e324fbcf8576365bd773e212da08a34c7
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
<<<<<<< HEAD
                        new ResourceNotFoundException("Order", "id", orderId));

        order.setOrderStatus(status);
        orderRepository.save(order);

        return mapToResponse(order);
=======
                        new ResourceNotFoundException("Order not found with id " + orderId));

        order.setOrderStatus(status);

        return mapToResponse(orderRepository.save(order));
>>>>>>> 8c577e4e324fbcf8576365bd773e212da08a34c7
    }

    // ✅ ENTITY → DTO
    private OrderResponse mapToResponse(Order order) {

<<<<<<< HEAD
        List<OrderItemResponse> itemResponses = order.getItems()
                .stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .itemName(item.getItemName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());
=======
        List<OrderItemResponse> itemResponses = order.getItems() == null
                ? List.of()
                : order.getItems()
                        .stream()
                        .map(item -> OrderItemResponse.builder()
                                .id(item.getId())
                                .itemName(item.getItemName())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .build())
                        .collect(Collectors.toList());
>>>>>>> 8c577e4e324fbcf8576365bd773e212da08a34c7

        return OrderResponse.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus())
                .paymentStatus(order.getPaymentStatus())
                .createdAt(order.getCreatedAt())
<<<<<<< HEAD
                .userEmail(order.getUser().getEmail())
                .restaurantName(order.getRestaurant().getName())
=======
                .userEmail(order.getUser() != null ? order.getUser().getEmail() : null)
                .restaurantName(order.getRestaurant() != null ? order.getRestaurant().getName() : null)
>>>>>>> 8c577e4e324fbcf8576365bd773e212da08a34c7
                .items(itemResponses)
                .build();
    }
}