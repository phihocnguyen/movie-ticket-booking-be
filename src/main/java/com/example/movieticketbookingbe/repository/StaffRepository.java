package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    List<Staff> findByIsActiveTrue();

    List<Staff> findByTheaterId(Long theaterId);

    Optional<Staff> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    boolean existsByTheaterIdAndPosition(Long theaterId, String position);
}