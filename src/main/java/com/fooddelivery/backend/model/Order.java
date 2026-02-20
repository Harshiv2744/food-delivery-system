package com.fooddelivery.backend.model;

import com.fooddelivery.backend.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
<<<<<<< HEAD
@Table(name = "orders") 
=======
@Table(name = "orders")
>>>>>>> f5b49cb0862004dca76b729cf222dfe59f7d6ae0
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createdAt;

    // 🔥 Relationship with User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 🔥 Relationship with Restaurant
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
