package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.seattype.SeatTypeDTO;
import com.example.movieticketbookingbe.dto.seattype.SeatTypeCreateDTO;
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

    public static SeatType toEntity(SeatTypeCreateDTO dto) {
        if (dto == null) return null;
        SeatType seatType = new SeatType();
        seatType.setName(dto.getName());
        seatType.setDescription(dto.getDescription());
        seatType.setPriceMultiplier(dto.getPriceMultiplier());
        seatType.setIsActive(dto.getIsActive());
        return seatType;
    }
}
