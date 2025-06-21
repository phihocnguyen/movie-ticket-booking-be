package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.SeatDTO;
import com.example.movieticketbookingbe.model.Seat;

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
} 
 