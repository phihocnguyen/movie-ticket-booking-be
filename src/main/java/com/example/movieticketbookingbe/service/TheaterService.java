package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.model.Theater;
import com.example.movieticketbookingbe.dto.theater.TheaterPatchDTO;

import java.util.List;
import java.util.Optional;

public interface TheaterService {
    Theater createTheater(Theater theater);

    Theater updateTheater(Long id, Theater theater);

    void deleteTheater(Long id);

    Optional<Theater> getTheaterById(Long id);

    List<Theater> getAllTheaters();

    List<Theater> getActiveTheaters();

    List<Theater> getTheatersByCity(String city);

    List<Theater> getTheatersByCountry(String country);

    Optional<Theater> getTheaterByNameAndCity(String name, String city);

    boolean existsByNameAndCity(String name, String city);

    List<Theater> searchTheaters(String name, String city, String state);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Theater patchTheater(Long id, TheaterPatchDTO patchDTO);
}