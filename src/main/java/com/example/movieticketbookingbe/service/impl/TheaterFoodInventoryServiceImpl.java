package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.TheaterFoodInventory;
import com.example.movieticketbookingbe.model.Theater;
import com.example.movieticketbookingbe.repository.TheaterFoodInventoryRepository;
import com.example.movieticketbookingbe.repository.TheaterRepository;
import com.example.movieticketbookingbe.service.TheaterFoodInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.movieticketbookingbe.dto.theaterfoodinventory.TheaterFoodInventoryPatchDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TheaterFoodInventoryServiceImpl implements TheaterFoodInventoryService {
    private final TheaterFoodInventoryRepository theaterFoodInventoryRepository;
    private final TheaterRepository theaterRepository;

    @Override
    public TheaterFoodInventory createTheaterFoodInventory(TheaterFoodInventory theaterFoodInventory) {
        LocalDateTime now = LocalDateTime.now();
        theaterFoodInventory.setCreatedAt(now);
        theaterFoodInventory.setUpdatedAt(now);
        return theaterFoodInventoryRepository.save(theaterFoodInventory);
    }

    @Override
    public List<TheaterFoodInventory> createMultipleTheaterFoodInventory(
            List<TheaterFoodInventory> theaterFoodInventories) {
        theaterFoodInventories.forEach(food -> {
            Theater theater = new Theater();
            theater.setId(food.getTheater().getId());
            food.setTheater(theater);
        });
        return theaterFoodInventoryRepository.saveAll(theaterFoodInventories);
    }

    @Override
    public TheaterFoodInventory updateTheaterFoodInventory(Long id, TheaterFoodInventory theaterFoodInventory) {
        TheaterFoodInventory existingTheaterFoodInventory = theaterFoodInventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TheaterFoodInventory not found"));
        theaterFoodInventory.setId(existingTheaterFoodInventory.getId());
        return theaterFoodInventoryRepository.save(theaterFoodInventory);
    }

    @Override
    public void deleteTheaterFoodInventory(Long id) {
        theaterFoodInventoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TheaterFoodInventory> getTheaterFoodInventoryById(Long id) {
        return theaterFoodInventoryRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TheaterFoodInventory> getAllTheaterFoodInventory() {
        return theaterFoodInventoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TheaterFoodInventory> getActiveTheaterFoodInventory() {
        return theaterFoodInventoryRepository.findByIsActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TheaterFoodInventory> getTheaterFoodInventoryByTheater(Long theaterId) {
        return theaterFoodInventoryRepository.findByTheaterId(theaterId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByTheaterAndName(Long theaterId, String name) {
        return theaterFoodInventoryRepository.existsByTheaterIdAndName(theaterId, name);
    }

    @Override
    public TheaterFoodInventory patchTheaterFoodInventory(Long id, TheaterFoodInventoryPatchDTO patchDTO) {
        TheaterFoodInventory entity = theaterFoodInventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TheaterFoodInventory not found"));

        if (patchDTO.getTheaterId() != null) entity.setTheaterId(patchDTO.getTheaterId());
        if (patchDTO.getName() != null) entity.setName(patchDTO.getName());
        if (patchDTO.getDescription() != null) entity.setDescription(patchDTO.getDescription());
        if (patchDTO.getPrice() != null) entity.setPrice(patchDTO.getPrice());
        if (patchDTO.getImageUrl() != null) entity.setImageUrl(patchDTO.getImageUrl());
        if (patchDTO.getCategory() != null) entity.setCategory(patchDTO.getCategory());
        if (patchDTO.getPreparationTime() != null) entity.setPreparationTime(patchDTO.getPreparationTime());
        if (patchDTO.getQuantity() != null) entity.setQuantity(patchDTO.getQuantity());
        if (patchDTO.getIsActive() != null) entity.setIsActive(patchDTO.getIsActive());

        entity.setUpdatedAt(LocalDateTime.now()); // cập nhật lại thời gian cập nhật

        return theaterFoodInventoryRepository.save(entity);
    }

    @Override
    public List<TheaterFoodInventory> getTheaterFoodInventoryByUserId(Long userId) {
        List<Theater> theaters = theaterRepository.findByTheaterOwnerId(userId);
        List<Long> theaterIds = theaters.stream().map(Theater::getId).toList();
        return theaterFoodInventoryRepository.findByTheaterIdIn(theaterIds);
    }
}