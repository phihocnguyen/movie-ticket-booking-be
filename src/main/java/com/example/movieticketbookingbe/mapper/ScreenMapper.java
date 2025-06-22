package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.ScreenDTO;
import com.example.movieticketbookingbe.dto.screen.ScreenCreateDTO;
import com.example.movieticketbookingbe.model.Screen;
import com.example.movieticketbookingbe.dto.TheaterDTO;

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
        dto.setTheater(screen.getTheater() != null ? TheaterMapper.toDTO(screen.getTheater()) : null);
        return dto;
    }

    public static Screen toEntity(ScreenCreateDTO dto) {
        if (dto == null) return null;
        Screen screen = new Screen();
        screen.setScreenName(dto.getScreenName());
        screen.setScreenType(dto.getScreenType());
        screen.setTotalSeats(dto.getTotalSeats());
        screen.setIsActive(dto.getIsActive());
        screen.setTheaterId(dto.getTheaterId());
        return screen;
    }
}
