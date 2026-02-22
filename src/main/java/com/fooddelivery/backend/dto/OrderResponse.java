package com.fooddelivery.backend.dto;

import com.fooddelivery.backend.model.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;

    private Double total;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private String userEmail;
}