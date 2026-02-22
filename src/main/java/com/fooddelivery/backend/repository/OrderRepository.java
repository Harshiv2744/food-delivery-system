package com.fooddelivery.backend.repository;

import com.fooddelivery.backend.model.Order;
import com.fooddelivery.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}