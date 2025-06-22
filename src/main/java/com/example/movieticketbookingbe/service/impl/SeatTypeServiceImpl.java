package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.SeatType;
import com.example.movieticketbookingbe.repository.SeatTypeRepository;
import com.example.movieticketbookingbe.service.SeatTypeService;
import com.example.movieticketbookingbe.dto.seattype.SeatTypePatchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatTypeServiceImpl implements SeatTypeService {
    private final SeatTypeRepository seatTypeRepository;

    @Override
    public SeatType createSeatType(SeatType seatType) {
        return seatTypeRepository.save(seatType);
    }

    @Override
    public SeatType updateSeatType(Long id, SeatType seatType) {
        SeatType existingSeatType = seatTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SeatType not found"));
        seatType.setId(existingSeatType.getId());
        return seatTypeRepository.save(seatType);
    }

    @Override
    public void deleteSeatType(Long id) {
        seatTypeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SeatType> getSeatTypeById(Long id) {
        return seatTypeRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatType> getAllSeatTypes() {
        return seatTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatType> getActiveSeatTypes() {
        return seatTypeRepository.findByIsActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SeatType> getSeatTypeByName(String name) {
        return seatTypeRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return seatTypeRepository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatType> searchSeatTypes(String name) {
        return seatTypeRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public SeatType patchSeatType(Long id, SeatTypePatchDTO patchDTO) {
        SeatType seatType = seatTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SeatType not found"));
        if (patchDTO.getName() != null) seatType.setName(patchDTO.getName());
        if (patchDTO.getPrice() != null) seatType.setPriceMultiplier(patchDTO.getPrice().doubleValue());
        if (patchDTO.getIsActive() != null) seatType.setIsActive(patchDTO.getIsActive());
        return seatTypeRepository.save(seatType);
    }
}