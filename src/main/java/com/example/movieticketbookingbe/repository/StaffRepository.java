package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.TheaterOwner;
import com.example.movieticketbookingbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheaterOwnerRepository extends JpaRepository<TheaterOwner, Long> {
    List<TheaterOwner> findByIsActiveTrue();
    List<TheaterOwner> findByUserRoleAndUserIsActiveTrue(User.UserRole role);
    Optional<TheaterOwner> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
}