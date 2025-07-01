package com.example.movieticketbookingbe.dto.showtime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ShowtimePatchDTO {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;
    
    private Float price;
    private Boolean isActive;
    private Long movieId;
    private Long screenId;
    private Long theaterId;

} 