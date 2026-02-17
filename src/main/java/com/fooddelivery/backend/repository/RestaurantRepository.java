package com.fooddelivery.backend.repository;

import com.fooddelivery.backend.model.RestaurantProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantProfile, Long> {
}

