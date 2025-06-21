package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.TheaterDTO;
import com.example.movieticketbookingbe.model.Theater;

public class TheaterMapper {
    public static TheaterDTO toDTO(Theater theater) {
        if (theater == null) return null;
        TheaterDTO dto = new TheaterDTO();
        dto.setId(theater.getId());
        dto.setName(theater.getName());
        dto.setAddress(theater.getAddress());
        dto.setCity(theater.getCity());
        dto.setState(theater.getState());
        dto.setCountry(theater.getCountry());
        dto.setZipCode(theater.getZipCode());
        dto.setPhoneNumber(theater.getPhoneNumber());
        dto.setEmail(theater.getEmail());
        dto.setIsActive(theater.getIsActive());
        dto.setOpeningTime(theater.getOpeningTime());
        dto.setClosingTime(theater.getClosingTime());
        dto.setTotalScreens(theater.getTotalScreens());
        dto.setCreatedAt(theater.getCreatedAt());
        dto.setUpdatedAt(theater.getUpdatedAt());
        return dto;
    }
} 