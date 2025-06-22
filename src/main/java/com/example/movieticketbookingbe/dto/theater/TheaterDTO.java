package com.example.movieticketbookingbe.dto.theater;

import com.example.movieticketbookingbe.dto.theaterowner.TheaterOwnerDTO;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class TheaterDTO {
    private Long id;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private TheaterOwnerDTO theaterOwner;
} 