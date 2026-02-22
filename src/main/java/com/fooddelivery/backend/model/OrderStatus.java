package com.fooddelivery.backend.model;
import com.fooddelivery.backend.model.OrderStatus;
public enum OrderStatus {
    PENDING,
    CONFIRMED,
    PREPARING,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}
