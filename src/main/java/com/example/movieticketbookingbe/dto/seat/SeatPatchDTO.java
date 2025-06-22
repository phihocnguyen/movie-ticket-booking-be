package com.example.movieticketbookingbe.dto.seat;

import lombok.Data;

@Data
public class SeatPatchDTO {
    private String seatNumber;
    private String seatType;
    private Boolean isActive;
    private Long screenId;
} 