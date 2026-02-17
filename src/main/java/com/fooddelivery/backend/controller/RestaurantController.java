package com.fooddelivery.backend.controller;

import com.fooddelivery.backend.model.RestaurantProfile;
import com.fooddelivery.backend.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public RestaurantProfile createRestaurant(@RequestBody RestaurantProfile restaurant) {
        restaurant.setCreatedAt(LocalDateTime.now());
        return restaurantService.createRestaurant(restaurant);
    }

    @GetMapping
    public List<RestaurantProfile> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public RestaurantProfile getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }
}

