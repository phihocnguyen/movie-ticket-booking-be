package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.model.TheaterFoodInventory;
import com.example.movieticketbookingbe.dto.theaterfoodinventory.TheaterFoodInventoryPatchDTO;
import java.util.List;
import java.util.Optional;

public interface TheaterFoodInventoryService {
    TheaterFoodInventory createTheaterFoodInventory(TheaterFoodInventory theaterFoodInventory);

    List<TheaterFoodInventory> createMultipleTheaterFoodInventory(List<TheaterFoodInventory> theaterFoodInventories);

    TheaterFoodInventory updateTheaterFoodInventory(Long id, TheaterFoodInventory theaterFoodInventory);

    void deleteTheaterFoodInventory(Long id);

    Optional<TheaterFoodInventory> getTheaterFoodInventoryById(Long id);

    List<TheaterFoodInventory> getAllTheaterFoodInventory();

    List<TheaterFoodInventory> getActiveTheaterFoodInventory();

    List<TheaterFoodInventory> getTheaterFoodInventoryByTheater(Long theaterId);

    boolean existsByTheaterAndName(Long theaterId, String name);

    TheaterFoodInventory patchTheaterFoodInventory(Long id, TheaterFoodInventoryPatchDTO patchDTO);
}