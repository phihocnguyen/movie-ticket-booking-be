package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.Seat;
import com.example.movieticketbookingbe.service.SeatService;
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

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
@Tag(name = "Seat", description = "Seat management APIs")
public class SeatController {
    private final SeatService seatService;

    @Operation(summary = "Create a new seat", description = "Creates a new seat with the provided information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seat created successfully", content = @Content(schema = @Schema(implementation = Seat.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Seat> createSeat(@RequestBody Seat seat) {
        return ResponseEntity.ok(seatService.createSeat(seat));
    }

    @Operation(summary = "Update a seat", description = "Updates an existing seat by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seat updated successfully"),
            @ApiResponse(responseCode = "404", description = "Seat not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Seat> updateSeat(
            @Parameter(description = "ID of the seat to update") @PathVariable Long id,
            @RequestBody Seat seat) {
        return ResponseEntity.ok(seatService.updateSeat(id, seat));
    }

    @Operation(summary = "Delete a seat", description = "Deletes a seat by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seat deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Seat not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(
            @Parameter(description = "ID of the seat to delete") @PathVariable Long id) {
        seatService.deleteSeat(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get a seat by ID", description = "Returns a seat by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seat found", content = @Content(schema = @Schema(implementation = Seat.class))),
            @ApiResponse(responseCode = "404", description = "Seat not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeatById(
            @Parameter(description = "ID of the seat to retrieve") @PathVariable Long id) {
        return seatService.getSeatById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all seats", description = "Returns a list of all seats")
    @ApiResponse(responseCode = "200", description = "List of seats retrieved successfully")
    @GetMapping
    public ResponseEntity<List<Seat>> getAllSeats() {
        return ResponseEntity.ok(seatService.getAllSeats());
    }

    @Operation(summary = "Get active seats", description = "Returns a list of all active seats")
    @ApiResponse(responseCode = "200", description = "List of active seats retrieved successfully")
    @GetMapping("/active")
    public ResponseEntity<List<Seat>> getActiveSeats() {
        return ResponseEntity.ok(seatService.getActiveSeats());
    }

    @Operation(summary = "Get seats by screen", description = "Returns a list of seats for a specific screen")
    @ApiResponse(responseCode = "200", description = "List of screen's seats retrieved successfully")
    @GetMapping("/screen/{screenId}")
    public ResponseEntity<List<Seat>> getSeatsByScreen(
            @Parameter(description = "ID of the screen") @PathVariable Long screenId) {
        return ResponseEntity.ok(seatService.getSeatsByScreen(screenId));
    }

    @Operation(summary = "Check if seat exists", description = "Checks if a seat number is already taken in a screen")
    @ApiResponse(responseCode = "200", description = "Seat check completed successfully")
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkSeatExists(
            @Parameter(description = "Seat number to check") @RequestParam String seatNumber,
            @Parameter(description = "ID of the screen") @RequestParam Long screenId) {
        return ResponseEntity.ok(seatService.existsByNumberAndScreen(seatNumber, screenId));
    }
}