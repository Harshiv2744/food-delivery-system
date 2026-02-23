package com.fooddelivery.backend.service;

import com.fooddelivery.backend.dto.OrderResponse;
import com.fooddelivery.backend.enums.DeliveryStatus;
import com.fooddelivery.backend.exception.ResourceNotFoundException;
import com.fooddelivery.backend.model.Delivery;
import com.fooddelivery.backend.model.Order;
import com.fooddelivery.backend.model.User;
import com.fooddelivery.backend.repository.DeliveryRepository;
import com.fooddelivery.backend.repository.OrderRepository;
import com.fooddelivery.backend.repository.UserRepository;
import com.fooddelivery.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;

    @Transactional
    public OrderResponse assignDeliveryAgent(Long orderId, Long deliveryAgentId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
        User agent = userRepository.findById(deliveryAgentId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + deliveryAgentId));
        if (order.getDelivery() != null) {
            throw new RuntimeException("Order already has a delivery assignment");
        }
        Delivery delivery = Delivery.builder()
                .order(order)
                .deliveryAgent(agent)
                .status(DeliveryStatus.ASSIGNED)
                .assignedAt(LocalDateTime.now())
                .build();
        delivery = deliveryRepository.save(delivery);
        order.setDelivery(delivery);
        orderRepository.save(order);
        return orderService.getOrderById(orderId);
    }

    public List<OrderResponse> getAssignedOrders(String deliveryAgentEmail) {
        User agent = userRepository.findByEmail(deliveryAgentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return deliveryRepository.findByDeliveryAgent(agent).stream()
                .map(d -> orderService.getOrderById(d.getOrder().getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse updateDeliveryStatus(Long orderId, DeliveryStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
        Delivery delivery = order.getDelivery();
        if (delivery == null) {
            throw new ResourceNotFoundException("No delivery assigned for this order");
        }
        delivery.setStatus(status);
        if (status == DeliveryStatus.DELIVERED) {
            delivery.setDeliveredAt(LocalDateTime.now());
            order.setOrderStatus(com.fooddelivery.backend.enums.OrderStatus.DELIVERED);
            orderRepository.save(order);
        }
        deliveryRepository.save(delivery);
        return orderService.getOrderById(orderId);
    }
}
