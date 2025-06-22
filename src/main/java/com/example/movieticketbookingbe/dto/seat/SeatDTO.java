package com.example.movieticketbookingbe.dto.seat;

import com.example.movieticketbookingbe.dto.screen.ScreenDTO;
import com.example.movieticketbookingbe.dto.seattype.SeatTypeDTO;
import lombok.Data;

@Data
public class SeatDTO {
    private Long id;
    private String seatNumber;
    private Boolean isAvailable;
    private ScreenDTO screen;
    private SeatTypeDTO seatType;
} 