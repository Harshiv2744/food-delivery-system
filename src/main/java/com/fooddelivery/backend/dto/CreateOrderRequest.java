package com.fooddelivery.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {

    @NotNull
    private Double totalAmount;

    @NotNull
    private Long userId;

    @NotNull
    private Long restaurantId;
}
