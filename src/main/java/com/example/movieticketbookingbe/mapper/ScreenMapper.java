 package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.ScreenDTO;
import com.example.movieticketbookingbe.model.Screen;

public class ScreenMapper {
    public static ScreenDTO toDTO(Screen screen) {
        if (screen == null) return null;
        ScreenDTO dto = new ScreenDTO();
        dto.setId(screen.getId());
        dto.setScreenName(screen.getScreenName());
        dto.setScreenType(screen.getScreenType());
        dto.setTotalSeats(screen.getTotalSeats());
        dto.setIsActive(screen.getIsActive());
        dto.setTheaterId(screen.getTheaterId());
        dto.setCreatedAt(screen.getCreatedAt());
        dto.setUpdatedAt(screen.getUpdatedAt());
        return dto;
    }
}
