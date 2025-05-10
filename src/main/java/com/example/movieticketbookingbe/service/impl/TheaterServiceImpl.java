package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.Theater;
import com.example.movieticketbookingbe.repository.TheaterRepository;
import com.example.movieticketbookingbe.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TheaterServiceImpl implements TheaterService {
    private final TheaterRepository theaterRepository;

    @Override
    public Theater createTheater(Theater theater) {
        return theaterRepository.save(theater);
    }

    @Override
    public Theater updateTheater(Long id, Theater theater) {
        Theater existingTheater = theaterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theater not found"));
        theater.setId(existingTheater.getId());
        return theaterRepository.save(theater);
    }

    @Override
    public void deleteTheater(Long id) {
        theaterRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Theater> getTheaterById(Long id) {
        return theaterRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Theater> getActiveTheaters() {
        return theaterRepository.findByIsActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Theater> getTheatersByCity(String city) {
        return theaterRepository.findByCity(city);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Theater> getTheatersByCountry(String country) {
        return theaterRepository.findByCountry(country);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Theater> getTheaterByNameAndCity(String name, String city) {
        return theaterRepository.findByNameAndCity(name, city);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNameAndCity(String name, String city) {
        return theaterRepository.existsByNameAndCity(name, city);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Theater> searchTheaters(String name, String city, String state) {
        return theaterRepository.findByNameContainingIgnoreCaseAndCityAndState(name, city, state);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return theaterRepository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return theaterRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByPhoneNumber(String phoneNumber) {
        return theaterRepository.existsByPhoneNumber(phoneNumber);
    }
}