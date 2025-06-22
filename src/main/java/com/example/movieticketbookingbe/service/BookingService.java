package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.model.Booking;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking createBooking(Booking booking);

    Booking updateBooking(Long id, Booking booking);

    void deleteBooking(Long id);

    Optional<Booking> getBookingById(Long id);

    List<Booking> getAllBookings();

    List<Booking> getActiveBookings();

    List<Booking> getBookingsByUser(Long userId);

    List<Booking> getBookingsByShowtime(Long showtimeId);

    List<Booking> searchBookings(Long userId, Long showtimeId, LocalDateTime startTime, LocalDateTime endTime);

    Booking cancelBooking(Long id);

    Booking completeBooking(Long id);

    Booking patchBooking(Long id, com.example.movieticketbookingbe.dto.booking.BookingPatchDTO patchDTO);
}