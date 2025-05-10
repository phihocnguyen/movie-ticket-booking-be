package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.Booking;
import com.example.movieticketbookingbe.model.Booking.BookingStatus;
import com.example.movieticketbookingbe.repository.BookingRepository;
import com.example.movieticketbookingbe.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(Long id, Booking booking) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setId(existingBooking.getId());
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getActiveBookings() {
        return bookingRepository.findByIsActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByShowtime(Long showtimeId) {
        return bookingRepository.findByShowtimeId(showtimeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> searchBookings(Long userId, Long showtimeId, LocalDateTime startTime, LocalDateTime endTime) {
        return bookingRepository.findByUserIdAndShowtimeIdAndBookingTimeBetween(userId, showtimeId, startTime, endTime);
    }

    @Override
    public Booking cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(BookingStatus.CANCELLED);
        booking.setIsActive(false);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking completeBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(BookingStatus.COMPLETED);
        return bookingRepository.save(booking);
    }
}