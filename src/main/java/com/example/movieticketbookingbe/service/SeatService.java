package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.model.Seat;
import java.util.List;
import java.util.Optional;

public interface SeatService {
    Seat createSeat(Seat seat);

    Seat updateSeat(Long id, Seat seat);

    void deleteSeat(Long id);

    Optional<Seat> getSeatById(Long id);

    List<Seat> getAllSeats();

    List<Seat> getSeatsByScreen(Long screenId);

    List<Seat> getSeatsBySeatType(Long seatTypeId);

    List<Seat> getActiveSeats();

    Optional<Seat> getSeatByNumberAndScreen(String seatNumber, Long screenId);

    boolean existsByNumberAndScreen(String seatNumber, Long screenId);

    List<Seat> searchSeats(String seatNumber, Long screenId);

    boolean existsByNumber(String seatNumber);

    Seat patchSeat(Long id, com.example.movieticketbookingbe.dto.seat.SeatPatchDTO patchDTO);
}