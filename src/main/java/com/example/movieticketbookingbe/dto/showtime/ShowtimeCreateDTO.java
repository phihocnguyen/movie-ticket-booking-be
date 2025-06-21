package com.example.movieticketbookingbe.dto.showtime;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ShowtimeCreateDTO {
    private Long movieId;
    private Long theaterId;
    private Long screenId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double price;
    private Boolean isActive;
} 