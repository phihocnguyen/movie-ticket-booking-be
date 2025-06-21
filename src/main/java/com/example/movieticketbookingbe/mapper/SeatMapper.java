package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.SeatDTO;
import com.example.movieticketbookingbe.model.Seat;
import com.example.movieticketbookingbe.dto.seat.SeatCreateDTO;

public class SeatMapper {
    public static SeatDTO toDTO(Seat seat) {
        if (seat == null) return null;
        SeatDTO dto = new SeatDTO();
        dto.setId(seat.getId());
        dto.setScreenId(seat.getScreen() != null ? seat.getScreen().getId() : null);
        dto.setSeatNumber(seat.getSeatNumber());
        dto.setSeatTypeId(seat.getSeatTypeId());
        dto.setIsActive(seat.getIsActive());
        dto.setSeatTypeName(seat.getSeatTypeName());
        dto.setPriceMultiplier(seat.getPriceMultiplier());
        return dto;
    }

    public static Seat toEntity(SeatCreateDTO dto) {
        if (dto == null) return null;
        Seat seat = new Seat();
        seat.setScreen(null); // cần set screenId, mapping ở service
        seat.setSeatNumber(dto.getSeatNumber());
        seat.setSeatTypeId(dto.getSeatTypeId());
        seat.setIsActive(dto.getIsActive());
        return seat;
    }
} 
 