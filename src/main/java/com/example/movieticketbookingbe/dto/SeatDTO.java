package com.example.movieticketbookingbe.dto;

import lombok.Data;

@Data
public class SeatDTO {
    private Long id;
    private Long screenId;
    private String seatNumber;
    private Long seatTypeId;
    private Boolean isActive;
    private String seatTypeName;
    private Double priceMultiplier;
} 