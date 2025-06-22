package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.theaterowner.TheaterOwnerCreateDTO;
import com.example.movieticketbookingbe.dto.theaterowner.TheaterOwnerDTO;
import com.example.movieticketbookingbe.model.TheaterOwner;
import com.example.movieticketbookingbe.dto.user.UserDTO;

public class TheaterOwnerMapper {
    public static TheaterOwnerDTO toDTO(TheaterOwner owner) {
        if (owner == null) return null;
        TheaterOwnerDTO dto = new TheaterOwnerDTO();
        dto.setId(owner.getId());
        dto.setUser(owner.getUser() != null ? com.example.movieticketbookingbe.mapper.UserMapper.toDTO(owner.getUser()) : null);
        dto.setIsActive(owner.getIsActive());
        dto.setCreatedAt(owner.getCreatedAt());
        dto.setUpdatedAt(owner.getUpdatedAt());
        return dto;
    }

    public static TheaterOwner toEntity(TheaterOwnerCreateDTO dto) {
        if (dto == null) return null;
        TheaterOwner owner = new TheaterOwner();
        owner.setUser(null);
        owner.setIsActive(dto.getIsActive());
        return owner;
    }
} 