package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.TheaterFoodInventoryDTO;
import com.example.movieticketbookingbe.model.TheaterFoodInventory;
import com.example.movieticketbookingbe.dto.theaterfoodinventory.TheaterFoodInventoryCreateDTO;
import com.example.movieticketbookingbe.dto.TheaterDTO;

public class TheaterFoodInventoryMapper {
    public static TheaterFoodInventoryDTO toDTO(TheaterFoodInventory entity) {
        if (entity == null) return null;
        TheaterFoodInventoryDTO dto = new TheaterFoodInventoryDTO();
        dto.setId(entity.getId());
        dto.setFoodName(entity.getFoodName());
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());
        dto.setTheater(entity.getTheater() != null ? TheaterMapper.toDTO(entity.getTheater()) : null);
        return dto;
    }

    public static TheaterFoodInventory toEntity(TheaterFoodInventoryCreateDTO dto) {
        if (dto == null) return null;
        TheaterFoodInventory food = new TheaterFoodInventory();
        food.setTheaterId(dto.getTheaterId());
        food.setName(dto.getName());
        food.setDescription(dto.getDescription());
        food.setPrice(dto.getPrice());
        food.setImageUrl(dto.getImageUrl());
        food.setCategory(dto.getCategory());
        food.setPreparationTime(dto.getPreparationTime());
        food.setQuantity(dto.getQuantity());
        food.setIsActive(dto.getIsActive());
        return food;
    }
}
