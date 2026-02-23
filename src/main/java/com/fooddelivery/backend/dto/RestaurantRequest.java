package com.fooddelivery.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RestaurantRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String address;

    private String phoneNumber;
}
