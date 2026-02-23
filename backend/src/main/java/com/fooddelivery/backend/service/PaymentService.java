package com.fooddelivery.backend.service;

import com.fooddelivery.backend.enums.PaymentStatus;
import com.fooddelivery.backend.exception.ResourceNotFoundException;
import com.fooddelivery.backend.model.Order;
import com.fooddelivery.backend.model.Payment;
import com.fooddelivery.backend.repository.OrderRepository;
import com.fooddelivery.backend.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public void updatePaymentStatus(Long orderId, PaymentStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
        order.setPaymentStatus(status);
        orderRepository.save(order);
        if (order.getPayment() != null) {
            order.getPayment().setStatus(status);
            if (status == PaymentStatus.COMPLETED) {
                order.getPayment().setPaidAt(LocalDateTime.now());
            }
            paymentRepository.save(order.getPayment());
        }
    }
}
