package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.Seat;
import com.example.movieticketbookingbe.repository.SeatRepository;
import com.example.movieticketbookingbe.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;

    @Override
    public Seat createSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    @Override
    public Seat updateSeat(Long id, Seat seat) {
        Seat existingSeat = seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        seat.setId(existingSeat.getId());
        return seatRepository.save(seat);
    }

    @Override
    public void deleteSeat(Long id) {
        seatRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Seat> getSeatById(Long id) {
        return seatRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Seat> getSeatsByScreen(Long screenId) {
        return seatRepository.findByScreenId(screenId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Seat> getSeatsBySeatType(Long seatTypeId) {
        return seatRepository.findBySeatTypeId(seatTypeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Seat> getActiveSeats() {
        return seatRepository.findByIsActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Seat> getSeatByNumberAndScreen(String seatNumber, Long screenId) {
        return seatRepository.findBySeatNumberAndScreenId(seatNumber, screenId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNumberAndScreen(String seatNumber, Long screenId) {
        return seatRepository.existsBySeatNumberAndScreenId(seatNumber, screenId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Seat> searchSeats(String seatNumber, Long screenId) {
        return seatRepository.findBySeatNumberContainingIgnoreCaseAndScreenId(seatNumber, screenId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNumber(String seatNumber) {
        return seatRepository.existsBySeatNumber(seatNumber);
    }

    @Override
    public Seat patchSeat(Long id, SeatPatchDTO patchDTO) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        if (patchDTO.getSeatNumber() != null) seat.setSeatNumber(patchDTO.getSeatNumber());
        if (patchDTO.getSeatType() != null) seat.setSeatType(patchDTO.getSeatType());
        if (patchDTO.getIsActive() != null) seat.setIsActive(patchDTO.getIsActive());
        if (patchDTO.getScreenId() != null) {
            // TODO: set screen entity nếu cần
        }
        return seatRepository.save(seat);
    }
}