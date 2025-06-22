package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.theater.TheaterDTO;
import com.example.movieticketbookingbe.model.Theater;
import com.example.movieticketbookingbe.dto.theater.TheaterCreateDTO;
import com.example.movieticketbookingbe.dto.theaterowner.TheaterOwnerDTO;

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
        if (theater.getTheaterOwner() != null &&
            theater.getTheaterOwner().getUser() != null &&
            theater.getTheaterOwner().getUser().getRole() == com.example.movieticketbookingbe.model.User.UserRole.THEATER_OWNER) {
            dto.setTheaterOwner(com.example.movieticketbookingbe.mapper.TheaterOwnerMapper.toDTO(theater.getTheaterOwner()));
        } else {
            dto.setTheaterOwner(null);
        }
        return dto;
    }

    public static Theater toEntity(TheaterCreateDTO dto) {
        if (dto == null) return null;
        Theater theater = new Theater();
        theater.setName(dto.getName());
        theater.setAddress(dto.getAddress());
        theater.setCity(dto.getCity());
        theater.setState(dto.getState());
        theater.setCountry(dto.getCountry());
        theater.setZipCode(dto.getZipCode());
        theater.setPhoneNumber(dto.getPhoneNumber());
        theater.setEmail(dto.getEmail());
        theater.setIsActive(dto.getIsActive());
        theater.setOpeningTime(dto.getOpeningTime());
        theater.setClosingTime(dto.getClosingTime());
        theater.setTotalScreens(dto.getTotalScreens());
        return theater;
    }
} 