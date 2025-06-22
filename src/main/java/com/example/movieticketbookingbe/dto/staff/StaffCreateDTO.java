package com.example.movieticketbookingbe.dto.theaterowner;

import lombok.Data;

@Data
public class TheaterOwnerCreateDTO {
    private Long userId;
    private String position;
    private Double salary;
    private Boolean isActive;
} 