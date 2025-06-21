package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.TheaterFoodInventoryDTO;
import com.example.movieticketbookingbe.model.TheaterFoodInventory;
import com.example.movieticketbookingbe.dto.theaterfoodinventory.TheaterFoodInventoryCreateDTO;

public class TheaterFoodInventoryMapper {
    public static TheaterFoodInventoryDTO toDTO(TheaterFoodInventory food) {
        if (food == null) return null;
        TheaterFoodInventoryDTO dto = new TheaterFoodInventoryDTO();
        dto.setId(food.getId());
        dto.setTheaterId(food.getTheaterId());
        dto.setName(food.getName());
        dto.setDescription(food.getDescription());
        dto.setPrice(food.getPrice());
        dto.setImageUrl(food.getImageUrl());
        dto.setCategory(food.getCategory());
        dto.setPreparationTime(food.getPreparationTime());
        dto.setQuantity(food.getQuantity());
        dto.setIsActive(food.getIsActive());
        dto.setCreatedAt(food.getCreatedAt());
        dto.setUpdatedAt(food.getUpdatedAt());
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
