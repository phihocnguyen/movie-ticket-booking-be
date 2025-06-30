package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.Booking;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);

    List<Booking> findByShowtimeId(Long showtimeId);

    List<Booking> findByIsActiveTrue();

    List<Booking> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Booking> findByStatus(String status);

    boolean existsByUserIdAndShowtimeId(Long userId, Long showtimeId);
    @Query("""
    SELECT b FROM Booking b
    JOIN b.showtime s
    JOIN s.theater t
    JOIN t.theaterOwner o
    WHERE o.id = :ownerId
""")
    List<Booking> findAllByOwnerId(@Param("ownerId") Long ownerId);
    List<Booking> findByUserIdAndShowtimeIdAndBookingTimeBetween(Long userId, Long showtimeId, LocalDateTime startTime,
            LocalDateTime endTime);
}