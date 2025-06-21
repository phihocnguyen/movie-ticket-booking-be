package com.example.movieticketbookingbe.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TheaterFoodInventoryDTO {
    private Long id;
    private Long theaterId;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String category;
    private Integer preparationTime;
    private Integer quantity;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 