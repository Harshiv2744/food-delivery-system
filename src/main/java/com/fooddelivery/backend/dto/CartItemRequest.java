package com.fooddelivery.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemRequest {

    @NotNull
    private Long menuItemId;

    @Positive
    private int quantity = 1;
}
