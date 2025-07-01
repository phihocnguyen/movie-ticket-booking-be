package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.Screen;
import com.example.movieticketbookingbe.model.Seat;
import com.example.movieticketbookingbe.model.SeatType;
import com.example.movieticketbookingbe.model.Theater;
import com.example.movieticketbookingbe.repository.ScreenRepository;
import com.example.movieticketbookingbe.repository.SeatRepository;
import com.example.movieticketbookingbe.repository.SeatTypeRepository;
import com.example.movieticketbookingbe.repository.TheaterRepository;
import com.example.movieticketbookingbe.service.ScreenService;
import com.example.movieticketbookingbe.dto.screen.ScreenPatchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScreenServiceImpl implements ScreenService {
    private final ScreenRepository screenRepository;
    private final SeatTypeRepository seatTypeRepository;
    private final SeatRepository seatRepository;
    private final TheaterRepository theaterRepository;

    @Override
    public Screen createScreen(Screen screen) {
        // Validate theater exists
        Theater theater = theaterRepository.findById(screen.getTheaterId())
                .orElseThrow(() -> new RuntimeException("Theater not found"));

        // Set the theater relationship
        screen.setTheater(theater);

        // Save the screen
        Screen savedScreen = screenRepository.save(screen);

        // Create default seat types and seats
        createDefaultSeatTypes(savedScreen);

        return savedScreen;
    }

    private void createDefaultSeatTypes(Screen screen) {
        // Get or create default seat types
        List<SeatType> seatTypes = new ArrayList<>();

        // Standard seats
        SeatType standard = seatTypeRepository.findByName("Standard")
                .orElseGet(() -> {
                    SeatType newType = new SeatType();
                    newType.setName("Standard");
                    newType.setDescription("Ghế thường");
                    newType.setPriceMultiplier(1.0);
                    newType.setIsActive(true);
                    return seatTypeRepository.save(newType);
                });
        seatTypes.add(standard);

        // VIP seats
        SeatType vip = seatTypeRepository.findByName("VIP")
                .orElseGet(() -> {
                    SeatType newType = new SeatType();
                    newType.setName("VIP");
                    newType.setDescription("Ghế VIP");
                    newType.setPriceMultiplier(1.5);
                    newType.setIsActive(true);
                    return seatTypeRepository.save(newType);
                });
        seatTypes.add(vip);

        // Couple seats
        SeatType couple = seatTypeRepository.findByName("Couple")
                .orElseGet(() -> {
                    SeatType newType = new SeatType();
                    newType.setName("Couple");
                    newType.setDescription("Ghế đôi");
                    newType.setPriceMultiplier(2.0);
                    newType.setIsActive(true);
                    return seatTypeRepository.save(newType);
                });
        seatTypes.add(couple);

        // Create seats
        createSeatsForScreen(screen, seatTypes);
    }

    private void createSeatsForScreen(Screen screen, List<SeatType> seatTypes) {
        int totalSeats = screen.getTotalSeats();
        int standardSeats = (int) Math.round(totalSeats * 0.3); // 30%
        int vipSeats = (int) Math.round(totalSeats * 0.6); // 60%
        int coupleSeats = totalSeats - standardSeats - vipSeats; // 10% (đảm bảo tổng đúng)

        List<Seat> seats = new ArrayList<>();
        char row = 'A';
        int seatNumber = 1;
        int maxSeatsPerRow = 10; // Giới hạn tối đa 10 cột mỗi hàng

        // Sinh ghế Standard trước (30%)
        for (int i = 0; i < standardSeats; i++) {
            Seat seat = new Seat();
            seat.setScreen(screen);
            seat.setSeatNumber(row + String.format("%02d", seatNumber));
            seat.setSeatType(seatTypes.get(0)); // Standard
            seat.setIsActive(true);
            seats.add(seat);
            seatNumber++;
            if (seatNumber > maxSeatsPerRow) { 
                row++; 
                seatNumber = 1; 
            }
        }
        
        // Sau đó sinh ghế VIP (60%)
        for (int i = 0; i < vipSeats; i++) {
            Seat seat = new Seat();
            seat.setScreen(screen);
            seat.setSeatNumber(row + String.format("%02d", seatNumber));
            seat.setSeatType(seatTypes.get(1)); // VIP
            seat.setIsActive(true);
            seats.add(seat);
            seatNumber++;
            if (seatNumber > maxSeatsPerRow) { 
                row++; 
                seatNumber = 1; 
            }
        }
        
        // Cuối cùng sinh ghế Couple (10%)
        for (int i = 0; i < coupleSeats; i++) {
            Seat seat = new Seat();
            seat.setScreen(screen);
            seat.setSeatNumber(row + String.format("%02d", seatNumber));
            seat.setSeatType(seatTypes.get(2)); // Couple
            seat.setIsActive(true);
            seats.add(seat);
            seatNumber++;
            if (seatNumber > maxSeatsPerRow) { 
                row++; 
                seatNumber = 1; 
            }
        }
        seatRepository.saveAll(seats);
    }

    @Override
    public Screen updateScreen(Long id, Screen screen) {
        Screen existingScreen = screenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Screen not found"));
        screen.setId(existingScreen.getId());
        return screenRepository.save(screen);
    }

    @Override
    public void deleteScreen(Long id) {
        screenRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Screen> getScreenById(Long id) {
        return screenRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Screen> getAllScreens() {
        return screenRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Screen> getScreensByTheater(Long theaterId) {
        return screenRepository.findByTheaterId(theaterId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Screen> getActiveScreens() {
        return screenRepository.findByIsActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Screen> getScreenByNameAndTheater(String screenName, Long theaterId) {
        return screenRepository.findByScreenNameAndTheaterId(screenName, theaterId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByScreenNameAndTheater(String screenName, Long theaterId) {
        return screenRepository.existsByScreenNameAndTheaterId(screenName, theaterId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Screen> searchScreens(String screenName, Long theaterId) {
        return screenRepository.findByScreenNameContainingIgnoreCaseAndTheaterId(screenName, theaterId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByScreenName(String screenName) {
        return screenRepository.existsByScreenName(screenName);
    }

    @Override
    public Screen patchScreen(Long id, ScreenPatchDTO patchDTO) {
        Screen screen = screenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Screen not found"));
        if (patchDTO.getScreenName() != null) screen.setScreenName(patchDTO.getScreenName());
        if (patchDTO.getCapacity() != null) screen.setTotalSeats(patchDTO.getCapacity());
        if (patchDTO.getIsActive() != null) screen.setIsActive(patchDTO.getIsActive());
        if (patchDTO.getTheaterId() != null) {
            // TODO: set theater entity nếu cần
        }
        if(patchDTO.getScreenType()!= null) screen.setScreenType(patchDTO.getScreenType());
        return screenRepository.save(screen);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Screen> getScreensByTheaterId(Long theaterId) {
        return screenRepository.findByTheaterId(theaterId);
    }
}