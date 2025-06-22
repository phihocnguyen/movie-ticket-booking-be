package com.example.movieticketbookingbe.dto.theater;

import lombok.Data;

@Data
public class TheaterPatchDTO {
    private String name;
    private String address;
    private String city;
    private String state;
    private String phoneNumber;
    private String email;
    private Boolean isActive;
} 