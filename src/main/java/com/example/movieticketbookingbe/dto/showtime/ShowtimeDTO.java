package com.example.movieticketbookingbe.dto.showtime;

import com.example.movieticketbookingbe.dto.movie.MovieDTO;
import com.example.movieticketbookingbe.dto.screen.ScreenDTO;
import com.example.movieticketbookingbe.dto.theater.TheaterDTO;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ShowtimeDTO {
    private Long id;
    private MovieDTO movie;
    private ScreenDTO screen;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private TheaterDTO theater;
} 