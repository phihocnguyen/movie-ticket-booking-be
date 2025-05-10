package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.model.BookingVoucher;
import java.util.List;
import java.util.Optional;

public interface BookingVoucherService {
    BookingVoucher createBookingVoucher(BookingVoucher bookingVoucher);

    BookingVoucher updateBookingVoucher(Long id, BookingVoucher bookingVoucher);

    void deleteBookingVoucher(Long id);

    Optional<BookingVoucher> getBookingVoucherById(Long id);

    List<BookingVoucher> getAllBookingVouchers();

    List<BookingVoucher> getBookingVouchersByBooking(Long bookingId);

    List<BookingVoucher> getBookingVouchersByVoucher(Long voucherId);

    boolean existsByBookingAndVoucher(Long bookingId, Long voucherId);
}