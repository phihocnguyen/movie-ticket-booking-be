package com.example.movieticketbookingbe.dto.booking;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingPatchDTO {
    private Long showtimeId;
    private Long userId;
    private Float totalPrice;
    private String paymentMethod;
    private String note;
    private Boolean isActive;
    private LocalDateTime bookingTime;
} 