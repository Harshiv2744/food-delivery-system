package com.fooddelivery.backend.service;

import com.fooddelivery.backend.dto.MenuItemRequest;
import com.fooddelivery.backend.dto.MenuItemResponse;
import com.fooddelivery.backend.exception.ResourceNotFoundException;
import com.fooddelivery.backend.model.MenuItem;
import com.fooddelivery.backend.model.Restaurant;
import com.fooddelivery.backend.repository.MenuItemRepository;
import com.fooddelivery.backend.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public MenuItemResponse create(Long restaurantId, MenuItemRequest request) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + restaurantId));
        MenuItem item = MenuItem.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .restaurant(restaurant)
                .build();
        return mapToResponse(menuItemRepository.save(item));
    }

    @Transactional
    public MenuItemResponse update(Long id, MenuItemRequest request) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id " + id));
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setCategory(request.getCategory());
        return mapToResponse(menuItemRepository.save(item));
    }

    public void delete(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("MenuItem not found with id " + id);
        }
        menuItemRepository.deleteById(id);
    }

    public MenuItemResponse findById(Long id) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id " + id));
        return mapToResponse(item);
    }

    public List<MenuItemResponse> findByRestaurantId(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<MenuItemResponse> findByRestaurantIdAndCategory(Long restaurantId, String category) {
        return menuItemRepository.findByRestaurantIdAndCategory(restaurantId, category).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private MenuItemResponse mapToResponse(MenuItem m) {
        return MenuItemResponse.builder()
                .id(m.getId())
                .name(m.getName())
                .description(m.getDescription())
                .price(m.getPrice())
                .category(m.getCategory())
                .restaurantId(m.getRestaurant() != null ? m.getRestaurant().getId() : null)
                .build();
    }
}
