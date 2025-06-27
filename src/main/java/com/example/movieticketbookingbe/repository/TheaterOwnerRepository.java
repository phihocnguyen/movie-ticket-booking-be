package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.TheaterOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheaterOwnerRepository extends JpaRepository<TheaterOwner, Long> {
    List<TheaterOwner> findByIsActiveTrue();
    Optional<TheaterOwner> findByUserId(Long userId);
    Optional<TheaterOwner> findByUserEmail(String Email);
    boolean existsByUserId(Long userId);
} 