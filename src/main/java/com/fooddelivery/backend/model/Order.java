package com.fooddelivery.backend.model;

import com.fooddelivery.backend.enums.OrderStatus;
import com.fooddelivery.backend.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
<<<<<<< HEAD

import com.fooddelivery.backend.enums.OrderStatus;
import com.fooddelivery.backend.enums.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Table(name = "orders")
@Data
=======

@Entity
@Getter
@Setter
>>>>>>> 8c577e4e324fbcf8576365bd773e212da08a34c7
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
<<<<<<< HEAD
=======

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
>>>>>>> 8c577e4e324fbcf8576365bd773e212da08a34c7

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    
    private LocalDateTime createdAt;
<<<<<<< HEAD
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;

        if (paymentStatus == null) {
            paymentStatus = PaymentStatus.PENDING;
        }

        if (orderStatus == null) {
            orderStatus = OrderStatus.PENDING;   // ✅ FIXED HERE
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
=======

    @ManyToOne
    private User user;

    @ManyToOne
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
>>>>>>> 8c577e4e324fbcf8576365bd773e212da08a34c7
}