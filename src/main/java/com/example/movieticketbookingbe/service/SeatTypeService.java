package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.model.SeatType;
import java.util.List;
import java.util.Optional;

public interface SeatTypeService {
    SeatType createSeatType(SeatType seatType);

    SeatType updateSeatType(Long id, SeatType seatType);

    void deleteSeatType(Long id);

    Optional<SeatType> getSeatTypeById(Long id);

    List<SeatType> getAllSeatTypes();

    List<SeatType> getActiveSeatTypes();

    Optional<SeatType> getSeatTypeByName(String name);

    boolean existsByName(String name);

    List<SeatType> searchSeatTypes(String name);
}