package com.fooddelivery.backend.repository;

import com.fooddelivery.backend.enums.OrderStatus;
import com.fooddelivery.backend.model.Order;
<<<<<<< HEAD

=======
>>>>>>> 8c577e4e324fbcf8576365bd773e212da08a34c7
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

<<<<<<< HEAD
    Page<Order> findAll(Pageable pageable);

=======
>>>>>>> 8c577e4e324fbcf8576365bd773e212da08a34c7
    Page<Order> findByOrderStatus(OrderStatus orderStatus, Pageable pageable);

    List<Order> findByUserEmail(String email);
}