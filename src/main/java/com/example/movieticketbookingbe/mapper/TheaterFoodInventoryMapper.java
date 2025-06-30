package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.theaterfoodinventory.TheaterFoodInventoryDTO;
import com.example.movieticketbookingbe.model.TheaterFoodInventory;
import com.example.movieticketbookingbe.dto.theater.TheaterDTO;
import com.example.movieticketbookingbe.dto.theaterfoodinventory.TheaterFoodInventoryCreateDTO;

public class TheaterFoodInventoryMapper {
    public static TheaterFoodInventoryDTO toDTO(TheaterFoodInventory entity) {
        if (entity == null) return null;

        TheaterFoodInventoryDTO dto = new TheaterFoodInventoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setFoodName(entity.getFoodName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setImageUrl(entity.getImageUrl());
        dto.setCategory(entity.getCategory());
        dto.setPreparationTime(entity.getPreparationTime());
        dto.setQuantity(entity.getQuantity());
        dto.setIsActive(entity.getIsActive());

        if (entity.getTheater() != null) {
            dto.setTheater(TheaterMapper.toDTO(entity.getTheater()));
        } else {
            dto.setTheater(null);
        }

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
