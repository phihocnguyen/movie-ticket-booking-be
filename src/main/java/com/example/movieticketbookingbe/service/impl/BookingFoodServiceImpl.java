package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.BookingFood;
import com.example.movieticketbookingbe.repository.BookingFoodRepository;
import com.example.movieticketbookingbe.service.BookingFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingFoodServiceImpl implements BookingFoodService {
    private final BookingFoodRepository bookingFoodRepository;

    @Override
    public BookingFood createBookingFood(BookingFood bookingFood) {
        return bookingFoodRepository.save(bookingFood);
    }

    @Override
    public BookingFood updateBookingFood(Long id, BookingFood bookingFood) {
        BookingFood existingBookingFood = bookingFoodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking food not found"));
        bookingFood.setId(existingBookingFood.getId());
        return bookingFoodRepository.save(bookingFood);
    }

    @Override
    public void deleteBookingFood(Long id) {
        bookingFoodRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookingFood> getBookingFoodById(Long id) {
        return bookingFoodRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingFood> getAllBookingFoods() {
        return bookingFoodRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingFood> getBookingFoodsByBooking(Long bookingId) {
        return bookingFoodRepository.findByBookingId(bookingId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingFood> getBookingFoodsByFoodInventory(Long foodInventoryId) {
        return bookingFoodRepository.findByFoodInventoryId(foodInventoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByBookingAndFoodInventory(Long bookingId, Long foodInventoryId) {
        return bookingFoodRepository.existsByBookingIdAndFoodInventoryId(bookingId, foodInventoryId);
    }
}