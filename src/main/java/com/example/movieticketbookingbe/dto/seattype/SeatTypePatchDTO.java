package com.example.movieticketbookingbe.dto.seattype;

import lombok.Data;

@Data
public class SeatTypePatchDTO {
    private String name;
    private Float price;
    private Boolean isActive;
} 