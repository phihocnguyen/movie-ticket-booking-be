package com.example.movieticketbookingbe.dto.theaterowner;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TheaterOwnerDTO {
    private Long id;
    private Long userId;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}