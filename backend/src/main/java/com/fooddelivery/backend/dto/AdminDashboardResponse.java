package com.fooddelivery.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminDashboardResponse {

    private long totalUsers;
    private long totalRestaurants;
    private long totalOrders;
    private double totalRevenue;
}
