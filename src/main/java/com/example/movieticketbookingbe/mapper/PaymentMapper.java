package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.payment.PaymentDTO;
import com.example.movieticketbookingbe.dto.payment.PaymentCreateDTO;
import com.example.movieticketbookingbe.model.Payment;
import com.example.movieticketbookingbe.model.Payment.PaymentMethod;
import com.example.movieticketbookingbe.model.Payment.PaymentStatus;
import com.example.movieticketbookingbe.dto.booking.BookingDTO;
import com.example.movieticketbookingbe.dto.user.UserDTO;

public class PaymentMapper {
    public static PaymentDTO toDTO(Payment payment) {
        if (payment == null) return null;
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        if (payment.getBooking() != null) {
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setId(payment.getBooking().getId());
            bookingDTO.setStatus(payment.getBooking().getStatus() != null ? payment.getBooking().getStatus().name() : null);
            bookingDTO.setTotalAmount(payment.getBooking().getTotalAmount());
            bookingDTO.setBookingTime(payment.getBooking().getBookingTime());
            dto.setBooking(bookingDTO);
        } else {
            dto.setBooking(null);
        }
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
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
        payment.setStatus(dto.getStatus());
        payment.setTransactionId(dto.getTransactionId());
        payment.setIsActive(dto.getIsActive());
        return payment;
    }
} 