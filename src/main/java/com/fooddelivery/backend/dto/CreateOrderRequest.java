package com.fooddelivery.backend.dto;

import lombok.Data;   // ✅ ADD THIS IMPORT

@Data
public class CreateOrderRequest {

    private Double totalAmount;
    private Long restaurantId;
}