package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.model.BookingFood;
import java.util.List;
import java.util.Optional;

public interface BookingFoodService {
    BookingFood createBookingFood(BookingFood bookingFood);

    BookingFood updateBookingFood(Long id, BookingFood bookingFood);

    void deleteBookingFood(Long id);

    Optional<BookingFood> getBookingFoodById(Long id);

    List<BookingFood> getAllBookingFoods();

    List<BookingFood> getBookingFoodsByBooking(Long bookingId);

    List<BookingFood> getBookingFoodsByFoodInventory(Long foodInventoryId);

    boolean existsByBookingAndFoodInventory(Long bookingId, Long foodInventoryId);
}