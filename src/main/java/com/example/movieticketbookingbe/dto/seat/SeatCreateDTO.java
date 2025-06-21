package com.example.movieticketbookingbe.dto.seat;

import lombok.Data;

@Data
public class SeatCreateDTO {
    private Long screenId;
    private String seatNumber;
    private Long seatTypeId;
    private Boolean isActive;
} 