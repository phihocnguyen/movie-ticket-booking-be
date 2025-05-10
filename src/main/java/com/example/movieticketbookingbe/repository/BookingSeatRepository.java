package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {
    List<BookingSeat> findByBookingId(Long bookingId);

    List<BookingSeat> findBySeatId(Long seatId);

    boolean existsByBookingIdAndSeatId(Long bookingId, Long seatId);
}