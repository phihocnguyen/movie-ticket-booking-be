package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.Booking;
import com.example.movieticketbookingbe.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking", description = "Booking management APIs")
public class BookingController {
    private final BookingService bookingService;

    @Operation(summary = "Create a new booking", description = "Creates a new booking with the provided information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking created successfully", content = @Content(schema = @Schema(implementation = Booking.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @Operation(summary = "Update a booking", description = "Updates an existing booking by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking updated successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(
            @Parameter(description = "ID of the booking to update") @PathVariable Long id,
            @RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.updateBooking(id, booking));
    }

    @Operation(summary = "Delete a booking", description = "Deletes a booking by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(
            @Parameter(description = "ID of the booking to delete") @PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get a booking by ID", description = "Returns a booking by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking found", content = @Content(schema = @Schema(implementation = Booking.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(
            @Parameter(description = "ID of the booking to retrieve") @PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all bookings", description = "Returns a list of all bookings")
    @ApiResponse(responseCode = "200", description = "List of bookings retrieved successfully")
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @Operation(summary = "Get active bookings", description = "Returns a list of all active bookings")
    @ApiResponse(responseCode = "200", description = "List of active bookings retrieved successfully")
    @GetMapping("/active")
    public ResponseEntity<List<Booking>> getActiveBookings() {
        return ResponseEntity.ok(bookingService.getActiveBookings());
    }

    @Operation(summary = "Get bookings by user", description = "Returns a list of bookings for a specific user")
    @ApiResponse(responseCode = "200", description = "List of user's bookings retrieved successfully")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUser(
            @Parameter(description = "ID of the user") @PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId));
    }

    @Operation(summary = "Get bookings by showtime", description = "Returns a list of bookings for a specific showtime")
    @ApiResponse(responseCode = "200", description = "List of showtime bookings retrieved successfully")
    @GetMapping("/showtime/{showtimeId}")
    public ResponseEntity<List<Booking>> getBookingsByShowtime(
            @Parameter(description = "ID of the showtime") @PathVariable Long showtimeId) {
        return ResponseEntity.ok(bookingService.getBookingsByShowtime(showtimeId));
    }

    @Operation(summary = "Search bookings", description = "Search bookings by various criteria")
    @ApiResponse(responseCode = "200", description = "Search results retrieved successfully")
    @GetMapping("/search")
    public ResponseEntity<List<Booking>> searchBookings(
            @Parameter(description = "User ID to filter by") @RequestParam(required = false) Long userId,
            @Parameter(description = "Showtime ID to filter by") @RequestParam(required = false) Long showtimeId,
            @Parameter(description = "Start time for booking search") @RequestParam(required = false) LocalDateTime startTime,
            @Parameter(description = "End time for booking search") @RequestParam(required = false) LocalDateTime endTime) {
        return ResponseEntity.ok(bookingService.searchBookings(userId, showtimeId, startTime, endTime));
    }

    @Operation(summary = "Cancel a booking", description = "Cancels an existing booking")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancelBooking(
            @Parameter(description = "ID of the booking to cancel") @PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    @Operation(summary = "Complete a booking", description = "Marks a booking as completed")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking completed successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @PutMapping("/{id}/complete")
    public ResponseEntity<Booking> completeBooking(
            @Parameter(description = "ID of the booking to complete") @PathVariable Long id) {
        return ResponseEntity.ok(bookingService.completeBooking(id));
    }
}