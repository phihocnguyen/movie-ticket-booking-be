package com.example.movieticketbookingbe.dto.showtime;

import lombok.Data;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class ShowtimeCreateDTO {
    private Long movieId;
    private Long theaterId;
    private Long screenId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;
    private Double price;
    private Boolean isActive;
} 