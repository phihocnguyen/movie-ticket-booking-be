package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.dto.BookingRequestDTO;
import com.example.movieticketbookingbe.model.Booking;
import com.example.movieticketbookingbe.model.Booking.BookingSeatInfo;
import com.example.movieticketbookingbe.model.Booking.BookingFoodInfo;
import com.example.movieticketbookingbe.service.BookingService;
import com.example.movieticketbookingbe.dto.BookingDTO;
import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import com.example.movieticketbookingbe.mapper.BookingMapper;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking", description = "Booking management APIs")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @Operation(summary = "Create a new booking", description = "Creates a new booking with seats and foods")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking created successfully", content = @Content(schema = @Schema(implementation = Booking.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Booking> createBooking(
            @Parameter(description = "Booking request data", required = true) @RequestBody BookingRequestDTO bookingRequest) {
        Booking booking = new Booking();
        booking.setUserId(bookingRequest.getUserId());
        booking.setShowtimeId(bookingRequest.getShowtimeId());
        booking.setBookingTime(bookingRequest.getBookingTime());
        booking.setTotalAmount(bookingRequest.getTotalAmount());
        booking.setStatus(bookingRequest.getStatus());
        booking.setIsActive(true);

        // Create booking seats
        if (bookingRequest.getBookingSeats() != null) {
            List<BookingSeatInfo> bookingSeats = new ArrayList<>();
            for (BookingRequestDTO.BookingSeatDTO seatDTO : bookingRequest.getBookingSeats()) {
                BookingSeatInfo bookingSeat = new BookingSeatInfo();
                bookingSeat.setSeatId(seatDTO.getSeatId());
                bookingSeat.setPrice(seatDTO.getPrice());
                bookingSeats.add(bookingSeat);
            }
            booking.setBookingSeats(bookingSeats);
        }

        // Create booking foods
        if (bookingRequest.getBookingFoods() != null) {
            List<BookingFoodInfo> bookingFoods = new ArrayList<>();
            for (BookingRequestDTO.BookingFoodDTO foodDTO : bookingRequest.getBookingFoods()) {
                BookingFoodInfo bookingFood = new BookingFoodInfo();
                bookingFood.setFoodInventoryId(foodDTO.getFoodInventoryId());
                bookingFood.setQuantity(foodDTO.getQuantity());
                bookingFood.setPrice(foodDTO.getPrice());
                bookingFoods.add(bookingFood);
            }
            booking.setBookingFoods(bookingFoods);
        }

        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @Operation(summary = "Update a booking", description = "Updates an existing booking by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking updated successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<BookingDTO>> updateBooking(
            @Parameter(description = "ID of the booking to update") @PathVariable Long id,
            @RequestBody Booking booking) {
        BookingDTO dto = BookingMapper.toDTO(bookingService.updateBooking(id, booking));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Booking updated successfully", dto));
    }

    @Operation(summary = "Delete a booking", description = "Deletes a booking by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteBooking(
            @Parameter(description = "ID of the booking to delete") @PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Booking deleted successfully", null));
    }

    @Operation(summary = "Get a booking by ID", description = "Returns a booking by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking found", content = @Content(schema = @Schema(implementation = Booking.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<BookingDTO>> getBookingById(
            @Parameter(description = "ID of the booking to retrieve") @PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(booking -> ResponseEntity.ok(new ApiResponseDTO<>(200, "Booking found", BookingMapper.toDTO(booking))))
                .orElse(ResponseEntity.ok(new ApiResponseDTO<>(404, "Booking not found", null)));
    }

    @Operation(summary = "Get all bookings", description = "Returns a list of all bookings")
    @ApiResponse(responseCode = "200", description = "List of bookings retrieved successfully")
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<BookingDTO>>> getAllBookings() {
        List<BookingDTO> dtos = bookingService.getAllBookings().stream().map(BookingMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of bookings retrieved successfully", dtos));
    }

    @Operation(summary = "Get active bookings", description = "Returns a list of all active bookings")
    @ApiResponse(responseCode = "200", description = "List of active bookings retrieved successfully")
    @GetMapping("/active")
    public ResponseEntity<ApiResponseDTO<List<BookingDTO>>> getActiveBookings() {
        List<BookingDTO> dtos = bookingService.getActiveBookings().stream().map(BookingMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of active bookings retrieved successfully", dtos));
    }

    @Operation(summary = "Get bookings by user", description = "Returns a list of bookings for a specific user")
    @ApiResponse(responseCode = "200", description = "List of user's bookings retrieved successfully")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDTO<List<BookingDTO>>> getBookingsByUser(
            @Parameter(description = "ID of the user") @PathVariable Long userId) {
        List<BookingDTO> dtos = bookingService.getBookingsByUser(userId).stream().map(BookingMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of user's bookings retrieved successfully", dtos));
    }

    @Operation(summary = "Get bookings by showtime", description = "Returns a list of bookings for a specific showtime")
    @ApiResponse(responseCode = "200", description = "List of showtime bookings retrieved successfully")
    @GetMapping("/showtime/{showtimeId}")
    public ResponseEntity<ApiResponseDTO<List<BookingDTO>>> getBookingsByShowtime(
            @Parameter(description = "ID of the showtime") @PathVariable Long showtimeId) {
        List<BookingDTO> dtos = bookingService.getBookingsByShowtime(showtimeId).stream().map(BookingMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of showtime bookings retrieved successfully", dtos));
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