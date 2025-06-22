package com.example.movieticketbookingbe.dto.theaterowner;

import lombok.Data;

@Data
public class TheaterOwnerCreateDTO {
    private Long userId;
    private Boolean isActive;
} 