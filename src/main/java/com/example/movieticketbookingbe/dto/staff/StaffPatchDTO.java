package com.example.movieticketbookingbe.dto.theaterowner;

import lombok.Data;

@Data
public class TheaterOwnerPatchDTO {
    private String position;
    private Boolean isActive;
    private Long userId;
} 