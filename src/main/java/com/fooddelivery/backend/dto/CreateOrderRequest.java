package com.fooddelivery.backend.dto;

import lombok.Data;   // ✅ ADD THIS IMPORT
import jakarta.validation.constraints.*;

@Data
public class CreateOrderRequest {

    @NotNull
    @Positive
    private Double totalAmount;

    @NotNull
    private Long restaurantId;
}