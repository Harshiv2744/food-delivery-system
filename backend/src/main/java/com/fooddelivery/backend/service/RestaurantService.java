package com.fooddelivery.backend.service;

import com.fooddelivery.backend.dto.RestaurantRequest;
import com.fooddelivery.backend.dto.RestaurantResponse;
import com.fooddelivery.backend.enums.RestaurantStatus;
import com.fooddelivery.backend.exception.ResourceNotFoundException;
import com.fooddelivery.backend.model.Restaurant;
import com.fooddelivery.backend.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public RestaurantResponse create(RestaurantRequest request) {
        Restaurant restaurant = Restaurant.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .status(RestaurantStatus.PENDING)
                .build();
        return mapToResponse(restaurantRepository.save(restaurant));
    }

    @Transactional
    public RestaurantResponse update(Long id, RestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + id));
        restaurant.setName(request.getName());
        restaurant.setAddress(request.getAddress());
        restaurant.setPhoneNumber(request.getPhoneNumber());
        return mapToResponse(restaurantRepository.save(restaurant));
    }

    public void delete(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Restaurant not found with id " + id);
        }
        restaurantRepository.deleteById(id);
    }

    public List<RestaurantResponse> findAll() {
        return restaurantRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<RestaurantResponse> findByStatus(RestaurantStatus status) {
        return restaurantRepository.findByStatus(status).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<RestaurantResponse> findApproved() {
        return findByStatus(RestaurantStatus.APPROVED);
    }

    public RestaurantResponse findById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + id));
        return mapToResponse(restaurant);
    }

    @Transactional
    public RestaurantResponse approve(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + id));
        restaurant.setStatus(RestaurantStatus.APPROVED);
        return mapToResponse(restaurantRepository.save(restaurant));
    }

    @Transactional
    public RestaurantResponse reject(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + id));
        restaurant.setStatus(RestaurantStatus.REJECTED);
        return mapToResponse(restaurantRepository.save(restaurant));
    }

    private RestaurantResponse mapToResponse(Restaurant r) {
        return RestaurantResponse.builder()
                .id(r.getId())
                .name(r.getName())
                .address(r.getAddress())
                .phoneNumber(r.getPhoneNumber())
                .status(r.getStatus())
                .build();
    }
}
