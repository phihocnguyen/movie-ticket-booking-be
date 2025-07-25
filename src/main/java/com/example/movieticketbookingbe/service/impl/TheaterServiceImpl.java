package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.dto.theater.TheaterCreateDTO;
import com.example.movieticketbookingbe.model.Theater;
import com.example.movieticketbookingbe.repository.TheaterRepository;
import com.example.movieticketbookingbe.service.TheaterService;
import com.example.movieticketbookingbe.dto.theater.TheaterPatchDTO;
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
    public boolean existsByAddressAndOwner(String address, Long ownerId) {
        return theaterRepository.existsByAddressAndTheaterOwnerId(address, ownerId);
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

    @Override
    public Theater patchTheater(Long id, TheaterCreateDTO patchDTO) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theater not found"));

        if (patchDTO.getName() != null) theater.setName(patchDTO.getName());
        if (patchDTO.getAddress() != null) theater.setAddress(patchDTO.getAddress());
        if (patchDTO.getCity() != null) theater.setCity(patchDTO.getCity());
        if (patchDTO.getState() != null) theater.setState(patchDTO.getState());
        if (patchDTO.getCountry() != null) theater.setCountry(patchDTO.getCountry());
        if (patchDTO.getZipCode() != null) theater.setZipCode(patchDTO.getZipCode());
        if (patchDTO.getPhoneNumber() != null) theater.setPhoneNumber(patchDTO.getPhoneNumber());
        if (patchDTO.getEmail() != null) theater.setEmail(patchDTO.getEmail());
        if (patchDTO.getIsActive() != null) theater.setIsActive(patchDTO.getIsActive());
        if (patchDTO.getOpeningTime() != null) theater.setOpeningTime(patchDTO.getOpeningTime());
        if (patchDTO.getClosingTime() != null) theater.setClosingTime(patchDTO.getClosingTime());
        if (patchDTO.getTotalScreens() != null) theater.setTotalScreens(patchDTO.getTotalScreens());
        if (patchDTO.getTheaterOwnerId() != null) theater.setTheaterOwnerId(patchDTO.getTheaterOwnerId());

        return theaterRepository.save(theater);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Theater> getTheatersByTheaterOwnerId(Long theaterOwnerId) {
        return theaterRepository.findByTheaterOwnerId(theaterOwnerId);
    }
}