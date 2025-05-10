package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, Long> {
    List<SeatType> findByIsActiveTrue();

    Optional<SeatType> findByName(String name);

    boolean existsByName(String name);

    List<SeatType> findByNameContainingIgnoreCase(String name);
}