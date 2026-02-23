package com.fooddelivery.backend.service;

import com.fooddelivery.backend.dto.AdminDashboardResponse;
import com.fooddelivery.backend.repository.OrderRepository;
import com.fooddelivery.backend.repository.RestaurantRepository;
import com.fooddelivery.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;

    public AdminDashboardResponse getDashboardStats() {
        long totalUsers = userRepository.count();
        long totalRestaurants = restaurantRepository.count();
        long totalOrders = orderRepository.count();
        double totalRevenue = orderRepository.findAll().stream()
                .filter(o -> o.getPaymentStatus() == com.fooddelivery.backend.enums.PaymentStatus.COMPLETED)
                .mapToDouble(o -> o.getTotalAmount() != null ? o.getTotalAmount() : 0)
                .sum();
        return AdminDashboardResponse.builder()
                .totalUsers(totalUsers)
                .totalRestaurants(totalRestaurants)
                .totalOrders(totalOrders)
                .totalRevenue(totalRevenue)
                .build();
    }
}
