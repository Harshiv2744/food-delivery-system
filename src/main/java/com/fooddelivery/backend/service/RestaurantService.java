package com.fooddelivery.backend.service;

import com.fooddelivery.backend.model.RestaurantProfile;
import com.fooddelivery.backend.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantProfile createRestaurant(RestaurantProfile restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public List<RestaurantProfile> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public RestaurantProfile getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }
}

