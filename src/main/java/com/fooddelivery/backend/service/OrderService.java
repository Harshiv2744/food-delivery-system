package com.fooddelivery.backend.service;

import com.fooddelivery.backend.dto.OrderItemResponse;
import com.fooddelivery.backend.dto.OrderResponse;
import com.fooddelivery.backend.enums.OrderStatus;
import com.fooddelivery.backend.enums.PaymentStatus;
import com.fooddelivery.backend.exception.ResourceNotFoundException;
import com.fooddelivery.backend.model.*;
import com.fooddelivery.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PaymentRepository paymentRepository;

    // ✅ GET ALL ORDERS
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    // ✅ FILTER BY STATUS
    public Page<OrderResponse> getOrdersByStatus(OrderStatus status, Pageable pageable) {
        return orderRepository.findByOrderStatus(status, pageable)
                .map(this::mapToResponse);
    }

    // ✅ GET MY ORDERS
    public List<OrderResponse> getMyOrders(String email) {
        return orderRepository.findByUserEmail(email)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ PROCESS PAYMENT
    @Transactional
    public OrderResponse processPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found with id " + orderId));
        if (order.getPaymentStatus() == PaymentStatus.COMPLETED) {
            throw new RuntimeException("Payment already completed for this order");
        }
        order.setPaymentStatus(PaymentStatus.COMPLETED);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        if (order.getPayment() != null) {
            order.getPayment().setStatus(PaymentStatus.COMPLETED);
            order.getPayment().setPaidAt(LocalDateTime.now());
            paymentRepository.save(order.getPayment());
        }
        return mapToResponse(orderRepository.save(order));
    }

    // ✅ UPDATE ORDER STATUS
    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found with id " + orderId));
        order.setOrderStatus(status);
        return mapToResponse(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse placeOrderFromCart(String userEmail, Long restaurantId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + restaurantId));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart is empty"));
        List<CartItem> restaurantItems = cart.getItems().stream()
                .filter(ci -> ci.getMenuItem().getRestaurant().getId().equals(restaurantId))
                .collect(Collectors.toList());
        if (restaurantItems.isEmpty()) {
            throw new RuntimeException("No items from this restaurant in cart");
        }
        double totalAmount = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem ci : restaurantItems) {
            double lineTotal = ci.getMenuItem().getPrice() * ci.getQuantity();
            totalAmount += lineTotal;
            OrderItem oi = OrderItem.builder()
                    .menuItem(ci.getMenuItem())
                    .itemName(ci.getMenuItem().getName())
                    .quantity(ci.getQuantity())
                    .price(ci.getMenuItem().getPrice())
                    .build();
            orderItems.add(oi);
        }
        Order order = Order.builder()
                .user(user)
                .restaurant(restaurant)
                .totalAmount(totalAmount)
                .orderStatus(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        order = orderRepository.save(order);
        for (OrderItem oi : orderItems) {
            oi.setOrder(order);
        }
        order.setItems(orderItems);
        orderRepository.save(order);
        Payment payment = Payment.builder()
                .order(order)
                .amount(totalAmount)
                .status(PaymentStatus.PENDING)
                .build();
        payment = paymentRepository.save(payment);
        order.setPayment(payment);
        orderRepository.save(order);
        cart.getItems().removeAll(restaurantItems);
        cartRepository.save(cart);
        return mapToResponse(order);
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
        return mapToResponse(order);
    }

    public List<OrderResponse> getOrdersByRestaurantId(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ ENTITY → DTO
    private OrderResponse mapToResponse(Order order) {

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

        return OrderResponse.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus())
                .paymentStatus(order.getPaymentStatus())
                .createdAt(order.getCreatedAt())
                .userEmail(order.getUser() != null ? order.getUser().getEmail() : null)
                .restaurantName(order.getRestaurant() != null ? order.getRestaurant().getName() : null)
                .items(itemResponses)
                .build();
    }
}