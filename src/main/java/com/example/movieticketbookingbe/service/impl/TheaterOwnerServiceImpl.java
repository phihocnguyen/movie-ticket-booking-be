package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.TheaterOwner;
import com.example.movieticketbookingbe.repository.TheaterOwnerRepository;
import com.example.movieticketbookingbe.service.TheaterOwnerService;
import com.example.movieticketbookingbe.dto.theaterowner.TheaterOwnerPatchDTO;
import com.example.movieticketbookingbe.dto.theaterowner.TheaterOwnerWithUserCreateDTO;
import com.example.movieticketbookingbe.dto.theaterowner.TheaterOwnerDTO;
import com.example.movieticketbookingbe.dto.theaterowner.TheaterOwnerCreateDTO;
import com.example.movieticketbookingbe.dto.user.UserCreateDTO;
import com.example.movieticketbookingbe.model.User;
import com.example.movieticketbookingbe.service.UserService;
import com.example.movieticketbookingbe.mapper.UserMapper;
import com.example.movieticketbookingbe.mapper.TheaterOwnerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
@Transactional
public class TheaterOwnerServiceImpl implements TheaterOwnerService {
    private final TheaterOwnerRepository theaterOwnerRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TheaterOwner createTheaterOwner(TheaterOwner owner) {
        return theaterOwnerRepository.save(owner);
    }

    @Override
    public TheaterOwner updateTheaterOwner(Long id, TheaterOwner owner) {
        TheaterOwner existing = theaterOwnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TheaterOwner not found"));
        owner.setId(existing.getId());
        return theaterOwnerRepository.save(owner);
    }

    @Override
    public void deleteTheaterOwner(Long id) {
        theaterOwnerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TheaterOwner> getTheaterOwnerById(Long id) {
        return theaterOwnerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TheaterOwner> getAllTheaterOwners() {
        return theaterOwnerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TheaterOwner> getActiveTheaterOwners() {
        return theaterOwnerRepository.findByIsActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TheaterOwner> getTheaterOwnerByUser(Long userId) {
        return theaterOwnerRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUserId(Long userId) {
        return theaterOwnerRepository.existsByUserId(userId);
    }

    @Override
    public TheaterOwner patchTheaterOwner(Long id, TheaterOwnerPatchDTO patchDTO) {
        TheaterOwner owner = theaterOwnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TheaterOwner not found"));
        if (patchDTO.getIsActive() != null) owner.setIsActive(patchDTO.getIsActive());
        if (patchDTO.getUserId() != null) owner.setUser(null); // cần xử lý set user thực tế nếu cần
        return theaterOwnerRepository.save(owner);
    }

    @Override
    @Transactional
    public TheaterOwnerDTO createTheaterOwnerWithUser(TheaterOwnerWithUserCreateDTO dto) {
        UserCreateDTO userCreateDTO = dto.getUser();
        User user = UserMapper.toEntity(userCreateDTO);
        user = userService.createUser(user);
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        TheaterOwner owner = new TheaterOwner();
        owner.setUser(user);
        TheaterOwner savedOwner = theaterOwnerRepository.save(owner);

        return TheaterOwnerMapper.toDTO(savedOwner);
    }
} 