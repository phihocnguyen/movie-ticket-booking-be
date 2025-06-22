package com.example.movieticketbookingbe.dto.showtime;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ShowtimePatchDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Float price;
    private Boolean isActive;
    private Long movieId;
    private Long screenId;
} 