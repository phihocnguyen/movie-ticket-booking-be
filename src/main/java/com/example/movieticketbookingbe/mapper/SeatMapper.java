package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.seat.SeatDTO;
import com.example.movieticketbookingbe.model.Seat;
import com.example.movieticketbookingbe.dto.seat.SeatCreateDTO;
import com.example.movieticketbookingbe.dto.screen.ScreenDTO;
import com.example.movieticketbookingbe.dto.seattype.SeatTypeDTO;

public class SeatMapper {
    public static SeatDTO toDTO(Seat seat) {
        if (seat == null) return null;
        SeatDTO dto = new SeatDTO();
        dto.setId(seat.getId());
        dto.setSeatNumber(seat.getSeatNumber());
        dto.setIsAvailable(seat.getIsAvailable());
        dto.setScreen(seat.getScreen() != null ? ScreenMapper.toDTO(seat.getScreen()) : null);
        dto.setSeatType(seat.getSeatType() != null ? SeatTypeMapper.toDTO(seat.getSeatType()) : null);
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
 