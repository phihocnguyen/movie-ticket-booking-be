package com.example.movieticketbookingbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booking_foods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_inventory_id", nullable = false)
    private TheaterFoodInventory foodInventory;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double price;
}