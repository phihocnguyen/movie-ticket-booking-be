package com.example.movieticketbookingbe.dto.payment;

import com.example.movieticketbookingbe.dto.booking.BookingDTO;
import com.example.movieticketbookingbe.dto.user.UserDTO;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private Long id;
    private BookingDTO booking;
    private UserDTO user;
    private Double amount;
    private String status;
    private LocalDateTime paymentTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 