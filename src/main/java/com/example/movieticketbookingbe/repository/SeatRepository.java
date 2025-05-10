package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByScreenId(Long screenId);

    List<Seat> findByScreenIdAndIsActiveTrue(Long screenId);

    List<Seat> findByScreenIdAndSeatTypeId(Long screenId, Long seatTypeId);

    boolean existsByScreenIdAndSeatNumber(Long screenId, String seatNumber);

    List<Seat> findBySeatTypeId(Long seatTypeId);

    List<Seat> findByIsActiveTrue();

    Optional<Seat> findBySeatNumberAndScreenId(String seatNumber, Long screenId);

    boolean existsBySeatNumberAndScreenId(String seatNumber, Long screenId);

    List<Seat> findBySeatNumberContainingIgnoreCaseAndScreenId(String seatNumber, Long screenId);

    boolean existsBySeatNumber(String seatNumber);
}