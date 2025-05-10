package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.BookingFood;
import com.example.movieticketbookingbe.service.BookingFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-foods")
@RequiredArgsConstructor
public class BookingFoodController {
    private final BookingFoodService bookingFoodService;

    @PostMapping
    public ResponseEntity<BookingFood> createBookingFood(@RequestBody BookingFood bookingFood) {
        return ResponseEntity.ok(bookingFoodService.createBookingFood(bookingFood));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingFood> updateBookingFood(@PathVariable Long id, @RequestBody BookingFood bookingFood) {
        return ResponseEntity.ok(bookingFoodService.updateBookingFood(id, bookingFood));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookingFood(@PathVariable Long id) {
        bookingFoodService.deleteBookingFood(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingFood> getBookingFoodById(@PathVariable Long id) {
        return bookingFoodService.getBookingFoodById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<BookingFood>> getAllBookingFoods() {
        return ResponseEntity.ok(bookingFoodService.getAllBookingFoods());
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<BookingFood>> getBookingFoodsByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingFoodService.getBookingFoodsByBooking(bookingId));
    }
}