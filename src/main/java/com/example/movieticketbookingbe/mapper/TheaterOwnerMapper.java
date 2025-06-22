package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.theaterowner.TheaterOwnerCreateDTO;
import com.example.movieticketbookingbe.dto.theaterowner.TheaterOwnerDTO;
import com.example.movieticketbookingbe.model.TheaterOwner;

public class TheaterOwnerMapper {
    public static TheaterOwnerDTO toDTO(TheaterOwner owner) {
        if (owner == null) return null;
        TheaterOwnerDTO dto = new TheaterOwnerDTO();
        dto.setId(owner.getId());
        dto.setUserId(owner.getUser() != null ? owner.getUser().getId() : null);
        dto.setIsActive(owner.getIsActive());
        dto.setCreatedAt(owner.getCreatedAt());
        dto.setUpdatedAt(owner.getUpdatedAt());
        return dto;
    }

    public static TheaterOwner toEntity(TheaterOwnerCreateDTO dto) {
        if (dto == null) return null;
        TheaterOwner owner = new TheaterOwner();
        owner.setUser(null); // cần set user thực tế ở service
        owner.setIsActive(dto.getIsActive());
        return owner;
    }
} 