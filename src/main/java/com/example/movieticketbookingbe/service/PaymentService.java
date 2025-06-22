package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.model.Payment;
import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Payment createPayment(Payment payment);

    Payment updatePayment(Long id, Payment payment);

    void deletePayment(Long id);

    Optional<Payment> getPaymentById(Long id);

    List<Payment> getAllPayments();

    Payment getPaymentByBooking(Long bookingId);

    Payment completePayment(Long id);

    Payment failPayment(Long id);

    Payment refundPayment(Long id);

    Payment patchPayment(Long id, com.example.movieticketbookingbe.dto.payment.PaymentPatchDTO patchDTO);
}