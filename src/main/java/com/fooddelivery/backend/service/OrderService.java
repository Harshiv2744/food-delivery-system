package com.fooddelivery.backend.service;

import com.fooddelivery.backend.dto.OrderResponse;
import com.fooddelivery.backend.enums.OrderStatus;
import com.fooddelivery.backend.model.Order;
import com.fooddelivery.backend.model.User;
import com.fooddelivery.backend.model.Restaurant;
import com.fooddelivery.backend.repository.OrderRepository;
import com.fooddelivery.backend.repository.UserRepository;
import com.fooddelivery.backend.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    // Map Entity → DTO
    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .userId(order.getUser().getId())
                .restaurantId(order.getRestaurant().getId())
                .build();
    }

    // Create Order
   public OrderResponse createOrder(Double totalAmount, Long restaurantId) {

    String email = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new RuntimeException("Restaurant not found"));

    Order order = Order.builder()
            .totalAmount(totalAmount)
            .status(OrderStatus.PENDING)
            .createdAt(LocalDateTime.now())
            .user(user)
            .restaurant(restaurant)
            .build();

    return mapToResponse(orderRepository.save(order));
}

    public List<OrderResponse> getAllOrders() {

    String email = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // ADMIN → return all orders
    if (user.getRole().name().equals("ADMIN")) {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // USER → return only their orders
    return orderRepository.findByUserEmail(email)
            .stream()
            .map(this::mapToResponse)
            .toList();
}

    // Get Order By Id
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return mapToResponse(order);
    }

    public OrderResponse updateOrderStatus(Long id, OrderStatus status) {

    Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));

    order.setStatus(status);

    return mapToResponse(orderRepository.save(order));
}
}