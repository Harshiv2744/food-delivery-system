package com.fooddelivery.backend.repository;

import com.fooddelivery.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
