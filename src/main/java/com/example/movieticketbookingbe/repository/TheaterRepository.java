package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
    List<Theater> findByIsActiveTrue();

    List<Theater> findByCity(String city);

    List<Theater> findByCountry(String country);

    Optional<Theater> findByNameAndCity(String name, String city);

    boolean existsByNameAndCity(String name, String city);

    List<Theater> findByNameContainingIgnoreCaseAndCityAndState(String name, String city, String state);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    List<Theater> findByTheaterOwnerId(Long theaterOwnerId);
}