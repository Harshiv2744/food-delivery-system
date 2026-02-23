package com.fooddelivery.backend.repository;

import com.fooddelivery.backend.model.Delivery;
import com.fooddelivery.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByDeliveryAgent(User deliveryAgent);

    List<Delivery> findByDeliveryAgentId(Long deliveryAgentId);
}
