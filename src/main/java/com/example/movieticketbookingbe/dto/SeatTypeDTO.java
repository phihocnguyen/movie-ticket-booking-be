package com.example.movieticketbookingbe.dto;

import lombok.Data;

@Data
public class SeatTypeDTO {
    private Long id;
    private String name;
    private String description;
    private Double priceMultiplier;
    private Boolean isActive;
} 