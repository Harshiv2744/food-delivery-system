package com.fooddelivery.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemResponse {

    private Long id;
    private Long menuItemId;
    private String menuItemName;
    private Double price;
    private int quantity;
    private Double subtotal;
}
