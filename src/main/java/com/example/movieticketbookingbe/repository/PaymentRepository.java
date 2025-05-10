package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByIsActiveTrue();

    Optional<Payment> findByBookingId(Long bookingId);

    List<Payment> findByStatus(String status);

    boolean existsByBookingId(Long bookingId);
}