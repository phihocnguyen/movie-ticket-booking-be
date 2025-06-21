package com.example.movieticketbookingbe.dto.theaterfoodinventory;

import lombok.Data;

@Data
public class TheaterFoodInventoryCreateDTO {
    private Long theaterId;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String category;
    private Integer preparationTime;
    private Integer quantity;
    private Boolean isActive;
} 