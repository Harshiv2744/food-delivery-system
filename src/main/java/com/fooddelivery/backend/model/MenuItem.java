package com.fooddelivery.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantProfile restaurant;
}
