package com.fooddelivery.backend.dto;

import com.fooddelivery.backend.enums.RestaurantStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantResponse {

    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private RestaurantStatus status;
}
