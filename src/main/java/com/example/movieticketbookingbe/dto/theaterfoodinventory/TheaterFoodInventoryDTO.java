package com.example.movieticketbookingbe.dto.theaterfoodinventory;

import com.example.movieticketbookingbe.dto.theater.TheaterDTO;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TheaterFoodInventoryDTO {
    private Long id;
    private String foodName;
    private Integer quantity;
    private Double price;
    private TheaterDTO theater;
    private String name;
    private String description;
    private String imageUrl;
    private String category;
    private Integer preparationTime;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 