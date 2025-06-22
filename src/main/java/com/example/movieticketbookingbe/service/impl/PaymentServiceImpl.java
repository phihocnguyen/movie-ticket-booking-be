package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.Payment;
import com.example.movieticketbookingbe.repository.PaymentRepository;
import com.example.movieticketbookingbe.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Long id, Payment payment) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setId(existingPayment.getId());
        return paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Payment getPaymentByBooking(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Payment not found for booking"));
    }

    @Override
    public Payment completePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(Payment.PaymentStatus.COMPLETED);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment failPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(Payment.PaymentStatus.FAILED);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment refundPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(Payment.PaymentStatus.REFUNDED);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment patchPayment(Long id, PaymentPatchDTO patchDTO) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        if (patchDTO.getBookingId() != null) {
            // TODO: set booking entity nếu cần
        }
        if (patchDTO.getAmount() != null) payment.setAmount(patchDTO.getAmount());
        if (patchDTO.getMethod() != null) payment.setMethod(patchDTO.getMethod());
        if (patchDTO.getStatus() != null) payment.setStatus(patchDTO.getStatus());
        if (patchDTO.getPaymentTime() != null) payment.setPaymentTime(patchDTO.getPaymentTime());
        if (patchDTO.getIsActive() != null) payment.setIsActive(patchDTO.getIsActive());
        return paymentRepository.save(payment);
    }
}