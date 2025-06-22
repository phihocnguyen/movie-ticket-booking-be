package com.example.movieticketbookingbe.dto.payment;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PaymentPatchDTO {
    private Long bookingId;
    private Float amount;
    private String method;
    private String status;
    private LocalDateTime paymentTime;
    private Boolean isActive;
} 