package com.example.movieticketbookingbe.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ScreenDTO {
    private Long id;
    private String screenName;
    private String screenType;
    private Integer totalSeats;
    private Boolean isActive;
    private Long theaterId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 