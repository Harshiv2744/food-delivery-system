package com.fooddelivery.backend.controller;

import com.fooddelivery.backend.dto.CreateRestaurantRequest;
import com.fooddelivery.backend.model.RestaurantProfile;
import com.fooddelivery.backend.service.RestaurantService;
import jakarta.validation.Valid;   
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public RestaurantProfile createRestaurant(
            @Valid @RequestBody CreateRestaurantRequest request) {

        RestaurantProfile restaurant = RestaurantProfile.builder()
                .restaurantName(request.getRestaurantName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .isOpen(request.getIsOpen())
                .createdAt(LocalDateTime.now())
                .build();

        return restaurantService.createRestaurant(restaurant);
    }
}
