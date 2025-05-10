package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.BookingVoucher;
import com.example.movieticketbookingbe.service.BookingVoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-vouchers")
@RequiredArgsConstructor
public class BookingVoucherController {
    private final BookingVoucherService bookingVoucherService;

    @PostMapping
    public ResponseEntity<BookingVoucher> createBookingVoucher(@RequestBody BookingVoucher bookingVoucher) {
        return ResponseEntity.ok(bookingVoucherService.createBookingVoucher(bookingVoucher));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingVoucher> updateBookingVoucher(@PathVariable Long id,
            @RequestBody BookingVoucher bookingVoucher) {
        return ResponseEntity.ok(bookingVoucherService.updateBookingVoucher(id, bookingVoucher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookingVoucher(@PathVariable Long id) {
        bookingVoucherService.deleteBookingVoucher(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingVoucher> getBookingVoucherById(@PathVariable Long id) {
        return bookingVoucherService.getBookingVoucherById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<BookingVoucher>> getAllBookingVouchers() {
        return ResponseEntity.ok(bookingVoucherService.getAllBookingVouchers());
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<BookingVoucher>> getBookingVouchersByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingVoucherService.getBookingVouchersByBooking(bookingId));
    }

    @GetMapping("/voucher/{voucherId}")
    public ResponseEntity<List<BookingVoucher>> getBookingVouchersByVoucher(@PathVariable Long voucherId) {
        return ResponseEntity.ok(bookingVoucherService.getBookingVouchersByVoucher(voucherId));
    }
}