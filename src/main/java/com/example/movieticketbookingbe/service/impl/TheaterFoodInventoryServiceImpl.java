package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.TheaterFoodInventory;
import com.example.movieticketbookingbe.model.Theater;
import com.example.movieticketbookingbe.repository.TheaterFoodInventoryRepository;
import com.example.movieticketbookingbe.service.TheaterFoodInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TheaterFoodInventoryServiceImpl implements TheaterFoodInventoryService {
    private final TheaterFoodInventoryRepository theaterFoodInventoryRepository;

    @Override
    public TheaterFoodInventory createTheaterFoodInventory(TheaterFoodInventory theaterFoodInventory) {
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
}