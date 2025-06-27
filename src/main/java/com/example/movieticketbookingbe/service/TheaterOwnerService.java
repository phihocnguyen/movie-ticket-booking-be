package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.dto.theaterowner.TheaterOwnerWithUserCreateDTO;
import com.example.movieticketbookingbe.dto.theaterowner.TheaterOwnerDTO;
import com.example.movieticketbookingbe.dto.theaterowner.TheaterOwnerPatchDTO;
import com.example.movieticketbookingbe.model.TheaterOwner;
import java.util.List;
import java.util.Optional;

public interface TheaterOwnerService {
    TheaterOwner createTheaterOwner(TheaterOwner owner);
    TheaterOwner updateTheaterOwner(Long id, TheaterOwner owner);
    void deleteTheaterOwner(Long id);
    Optional<TheaterOwner> getTheaterOwnerById(Long id);
    Optional<TheaterOwner> getTheaterOwnerByEmail(String Email);
    List<TheaterOwner> getAllTheaterOwners();
    List<TheaterOwner> getActiveTheaterOwners();
    Optional<TheaterOwner> getTheaterOwnerByUser(Long userId);
    boolean existsByUserId(Long userId);
    TheaterOwner patchTheaterOwner(Long id, TheaterOwnerPatchDTO patchDTO);
    TheaterOwnerDTO createTheaterOwnerWithUser(TheaterOwnerWithUserCreateDTO dto);
} 