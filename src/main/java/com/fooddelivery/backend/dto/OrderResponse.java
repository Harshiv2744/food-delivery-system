package com.fooddelivery.backend.dto;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import com.fooddelivery.backend.enums.OrderStatus;
import com.fooddelivery.backend.enums.PaymentStatus;
=======
import com.fooddelivery.backend.enums.OrderStatus;
import com.fooddelivery.backend.enums.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
>>>>>>> 8c577e4e324fbcf8576365bd773e212da08a34c7

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
<<<<<<< HEAD

    private Double totalAmount;

    private OrderStatus orderStatus;

    private PaymentStatus paymentStatus;

=======
    private Double totalAmount;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
>>>>>>> 8c577e4e324fbcf8576365bd773e212da08a34c7
    private LocalDateTime createdAt;
    private String userEmail;
<<<<<<< HEAD

    private String restaurantName;

    // Optional: If you want to send order items in response
=======
    private String restaurantName;
>>>>>>> 8c577e4e324fbcf8576365bd773e212da08a34c7
    private List<OrderItemResponse> items;
}