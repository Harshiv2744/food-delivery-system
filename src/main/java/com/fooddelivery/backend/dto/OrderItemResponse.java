package com.fooddelivery.backend.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    private Long id;
    private String itemName;
    private int quantity;
    private double price;
}