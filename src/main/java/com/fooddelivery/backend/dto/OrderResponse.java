package com.fooddelivery.backend.dto;

import com.fooddelivery.backend.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderResponse {

    private Long id;
    private Double totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;

    private Long userId;
    private Long restaurantId;
}