package com.example.movieticketbookingbe.dto.theater;

import lombok.Data;
import java.time.LocalTime;

@Data
public class TheaterCreateDTO {
    private String name;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String phoneNumber;
    private String email;
    private Boolean isActive;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Integer totalScreens;
    private Long theaterOwnerId;
} 