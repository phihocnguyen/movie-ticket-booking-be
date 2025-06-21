package com.example.movieticketbookingbe.dto.staff;

import lombok.Data;

@Data
public class StaffCreateDTO {
    private Long theaterId;
    private Long userId;
    private String position;
    private Double salary;
    private Boolean isActive;
} 