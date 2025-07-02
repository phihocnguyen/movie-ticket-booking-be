package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.dto.DashBoard.TopTheaterDTO;
import com.example.movieticketbookingbe.model.Booking;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
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
    @Query("SELECT COALESCE(SUM(b.totalAmount), 0) FROM Booking b WHERE b.status = 'CONFIRMED'")
    long sumTotalRevenue();
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = 'CONFIRMED'")
    long countTicketsSold();

    @Query("SELECT SUM(b.totalAmount) FROM Booking b " +
            "WHERE b.status = 'CONFIRMED' AND b.bookingTime BETWEEN :start AND :end")
    Double sumRevenueByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT new com.example.movieticketbookingbe.dto.DashBoard.TopTheaterDTO(b.showtime.theater.name, SUM(b.totalAmount)) " +
            "FROM Booking b WHERE b.status = 'CONFIRMED' " +
            "GROUP BY b.showtime.theater.name " +
            "ORDER BY SUM(b.totalAmount) DESC")
    List<TopTheaterDTO> findTopTheatersByRevenue(Pageable pageable);

    @Query("SELECT SUM(b.totalAmount) FROM Booking b " +
            "WHERE b.status = 'CONFIRMED' AND b.showtime.theater.theaterOwnerId = :ownerId")
    Double getTotalRevenueByOwner(Long ownerId);

    @Query("SELECT COUNT(b.id) FROM Booking b " +
            "WHERE b.status = 'CONFIRMED' AND b.showtime.theater.theaterOwnerId = :ownerId")
    Long getTotalTicketsSoldByOwner(Long ownerId);


    @Query("SELECT SUM(b.totalAmount) FROM Booking b " +
            "WHERE b.status = 'CONFIRMED' " +
            "AND b.showtime.theater.theaterOwnerId = :ownerId " +
            "AND b.createdAt BETWEEN :startDate AND :endDate")
    Double sumRevenueByDateRangeAndOwner(@Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate,
                                         @Param("ownerId") Long ownerId);


    @Query("SELECT b FROM Booking b " +
            "JOIN FETCH b.showtime s " +
            "JOIN FETCH s.theater t " +
            "WHERE b.status = 'CONFIRMED' AND t.theaterOwnerId = :ownerId")
    List<Booking> findCompletedBookingsByOwner(@Param("ownerId") Long ownerId);
}