package com.example.movieticketbookingbe.event;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SeatBookingEvent {
    private Long seatId;
    private String seatNumber;
    private Long userId;
    private String eventType; // "LOCK", "UNLOCK", "BOOK"
    private String status; // "SUCCESS", "FAILED"
    private String message;
    private LocalDateTime timestamp;
}