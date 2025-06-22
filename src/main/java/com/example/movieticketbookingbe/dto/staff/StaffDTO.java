package com.example.movieticketbookingbe.dto.staff;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StaffDTO {
    private Long id;
    private Long theaterId;
    private Long userId;
    private String position;
    private Double salary;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 