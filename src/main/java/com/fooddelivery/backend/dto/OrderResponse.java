package com.fooddelivery.backend.dto;

import com.fooddelivery.backend.enums.OrderStatus;
import com.fooddelivery.backend.enums.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private Double totalAmount;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;
    private String userEmail;
    private String restaurantName;
    private List<OrderItemResponse> items;
}