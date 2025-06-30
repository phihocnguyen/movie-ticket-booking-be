package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.TheaterFoodInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterFoodInventoryRepository extends JpaRepository<TheaterFoodInventory, Long> {
    List<TheaterFoodInventory> findByIsActiveTrue();

    List<TheaterFoodInventory> findByTheaterId(Long theaterId);

    boolean existsByTheaterIdAndName(Long theaterId, String name);
    List<TheaterFoodInventory> findByTheaterIdIn(List<Long> theaterIds);

}