 package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.SeatTypeDTO;
import com.example.movieticketbookingbe.model.SeatType;

public class SeatTypeMapper {
    public static SeatTypeDTO toDTO(SeatType seatType) {
        if (seatType == null) return null;
        SeatTypeDTO dto = new SeatTypeDTO();
        dto.setId(seatType.getId());
        dto.setName(seatType.getName());
        dto.setDescription(seatType.getDescription());
        dto.setPriceMultiplier(seatType.getPriceMultiplier());
        dto.setIsActive(seatType.getIsActive());
        return dto;
    }
}
