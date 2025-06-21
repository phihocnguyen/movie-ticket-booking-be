package com.example.movieticketbookingbe.dto.payment;

import lombok.Data;

@Data
public class PaymentCreateDTO {
    private Long bookingId;
    private Double amount;
    private String paymentMethod;
    private String status;
    private String transactionId;
    private Boolean isActive;
} 