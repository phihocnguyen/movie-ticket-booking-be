package com.example.movieticketbookingbe.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ShowtimeDTO {
    private Long id;
    private Long movieId;
    private Long theaterId;
    private Long screenId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double price;
    private Boolean isActive;
    private String movieTitle;
    private String theaterName;
    private String screenName;
    private String theaterAddress;
} 