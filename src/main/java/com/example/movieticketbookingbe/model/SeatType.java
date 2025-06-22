package com.example.movieticketbookingbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@Entity
@Table(name = "seat_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SeatType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double priceMultiplier;

    @Column(nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "seatType")
    @JsonIgnore
    private List<Seat> seats;

    public void setPrice(Float price) {
        this.priceMultiplier = price.doubleValue();
    }
}