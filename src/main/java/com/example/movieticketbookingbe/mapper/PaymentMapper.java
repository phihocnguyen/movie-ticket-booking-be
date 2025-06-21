package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.PaymentDTO;
import com.example.movieticketbookingbe.dto.payment.PaymentCreateDTO;
import com.example.movieticketbookingbe.model.Payment;
import com.example.movieticketbookingbe.model.Payment.PaymentMethod;
import com.example.movieticketbookingbe.model.Payment.PaymentStatus;

public class PaymentMapper {
    public static PaymentDTO toDTO(Payment payment) {
        if (payment == null) return null;
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setBookingId(payment.getBooking() != null ? payment.getBooking().getId() : null);
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod() != null ? payment.getPaymentMethod().name() : null);
        dto.setStatus(payment.getStatus() != null ? payment.getStatus().name() : null);
        dto.setTransactionId(payment.getTransactionId());
        dto.setIsActive(payment.getIsActive());
        dto.setCreatedAt(payment.getCreatedAt());
        dto.setUpdatedAt(payment.getUpdatedAt());
        return dto;
    }

    public static Payment toEntity(PaymentCreateDTO dto) {
        if (dto == null) return null;
        Payment payment = new Payment();
        // payment.setBooking cần set ở service nếu cần
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod() != null ? PaymentMethod.valueOf(dto.getPaymentMethod()) : null);
        payment.setStatus(dto.getStatus() != null ? PaymentStatus.valueOf(dto.getStatus()) : null);
        payment.setTransactionId(dto.getTransactionId());
        payment.setIsActive(dto.getIsActive());
        return payment;
    }
} 