package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {
    List<Screen> findByTheaterId(Long theaterId);

    List<Screen> findByIsActiveTrue();

    Optional<Screen> findByScreenNameAndTheaterId(String screenName, Long theaterId);

    boolean existsByScreenNameAndTheaterId(String screenName, Long theaterId);

    List<Screen> findByScreenNameContainingIgnoreCaseAndTheaterId(String screenName, Long theaterId);

    boolean existsByScreenName(String screenName);
}