package com.example.movieticketbookingbe.dto.screen;

import lombok.Data;

@Data
public class ScreenPatchDTO {
    private String screenName;
    private Integer capacity;
    private Boolean isActive;
    private Long theaterId;
} 