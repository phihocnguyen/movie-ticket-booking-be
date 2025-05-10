package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.BookingVoucher;
import com.example.movieticketbookingbe.repository.BookingVoucherRepository;
import com.example.movieticketbookingbe.service.BookingVoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingVoucherServiceImpl implements BookingVoucherService {
    private final BookingVoucherRepository bookingVoucherRepository;

    @Override
    public BookingVoucher createBookingVoucher(BookingVoucher bookingVoucher) {
        return bookingVoucherRepository.save(bookingVoucher);
    }

    @Override
    public BookingVoucher updateBookingVoucher(Long id, BookingVoucher bookingVoucher) {
        BookingVoucher existingBookingVoucher = bookingVoucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BookingVoucher not found"));
        bookingVoucher.setId(existingBookingVoucher.getId());
        return bookingVoucherRepository.save(bookingVoucher);
    }

    @Override
    public void deleteBookingVoucher(Long id) {
        bookingVoucherRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookingVoucher> getBookingVoucherById(Long id) {
        return bookingVoucherRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingVoucher> getAllBookingVouchers() {
        return bookingVoucherRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingVoucher> getBookingVouchersByBooking(Long bookingId) {
        return bookingVoucherRepository.findByBookingId(bookingId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingVoucher> getBookingVouchersByVoucher(Long voucherId) {
        return bookingVoucherRepository.findByVoucherId(voucherId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByBookingAndVoucher(Long bookingId, Long voucherId) {
        return bookingVoucherRepository.existsByBookingIdAndVoucherId(bookingId, voucherId);
    }
}