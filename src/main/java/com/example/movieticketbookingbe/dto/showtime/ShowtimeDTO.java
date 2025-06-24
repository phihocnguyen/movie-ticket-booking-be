package com.example.movieticketbookingbe.dto.showtime;

import com.example.movieticketbookingbe.dto.movie.MovieDTO;
import com.example.movieticketbookingbe.dto.screen.ScreenDTO;
import com.example.movieticketbookingbe.dto.theater.TheaterDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ShowtimeDTO {
    private Long id;
    private MovieDTO movie;
    private ScreenDTO screen;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;
    
    private Double price;
    private Boolean isActive;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    
    private TheaterDTO theater;
} 