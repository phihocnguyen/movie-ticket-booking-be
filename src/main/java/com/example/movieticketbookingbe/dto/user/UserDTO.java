package com.example.movieticketbookingbe.dto.user;

import com.example.movieticketbookingbe.dto.booking.BookingDTO;
import com.example.movieticketbookingbe.dto.payment.PaymentDTO;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
    private Boolean isActive;
    private String username;
    private String fullName;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<BookingDTO> bookings;
    private List<PaymentDTO> payments;
}