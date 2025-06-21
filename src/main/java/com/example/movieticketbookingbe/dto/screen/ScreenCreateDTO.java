package com.example.movieticketbookingbe.dto.screen;

import lombok.Data;

@Data
public class ScreenCreateDTO {
    private String screenName;
    private String screenType;
    private Integer totalSeats;
    private Boolean isActive;
    private Long theaterId;
} 