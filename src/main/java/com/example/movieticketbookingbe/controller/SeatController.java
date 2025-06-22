package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.Seat;
import com.example.movieticketbookingbe.service.SeatService;
import com.example.movieticketbookingbe.dto.seat.SeatDTO;
import com.example.movieticketbookingbe.dto.seat.SeatCreateDTO;
import com.example.movieticketbookingbe.dto.seat.SeatPatchDTO;
import com.example.movieticketbookingbe.mapper.SeatMapper;
import com.example.movieticketbookingbe.dto.ApiResponseDTO;
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
    public ResponseEntity<ApiResponseDTO<SeatDTO>> createSeat(@RequestBody SeatCreateDTO seatCreateDTO) {
        Seat seat = SeatMapper.toEntity(seatCreateDTO);
        SeatDTO dto = SeatMapper.toDTO(seatService.createSeat(seat));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Seat created successfully", dto));
    }

    @Operation(summary = "Update a seat", description = "Updates an existing seat by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seat updated successfully"),
            @ApiResponse(responseCode = "404", description = "Seat not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<SeatDTO>> patchSeat(
            @Parameter(description = "ID of the seat to update") @PathVariable Long id,
            @RequestBody SeatPatchDTO patchDTO) {
        SeatDTO dto = SeatMapper.toDTO(seatService.patchSeat(id, patchDTO));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Seat updated successfully", dto));
    }

    @Operation(summary = "Delete a seat", description = "Deletes a seat by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seat deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Seat not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteSeat(
            @Parameter(description = "ID of the seat to delete") @PathVariable Long id) {
        seatService.deleteSeat(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Seat deleted successfully", null));
    }

    @Operation(summary = "Get a seat by ID", description = "Returns a seat by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seat found", content = @Content(schema = @Schema(implementation = Seat.class))),
            @ApiResponse(responseCode = "404", description = "Seat not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<SeatDTO>> getSeatById(
            @Parameter(description = "ID of the seat to retrieve") @PathVariable Long id) {
        return seatService.getSeatById(id)
                .map(seat -> ResponseEntity.ok(new ApiResponseDTO<>(200, "Seat found", SeatMapper.toDTO(seat))))
                .orElse(ResponseEntity.ok(new ApiResponseDTO<>(404, "Seat not found", null)));
    }

    @Operation(summary = "Get all seats", description = "Returns a list of all seats")
    @ApiResponse(responseCode = "200", description = "List of seats retrieved successfully")
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<SeatDTO>>> getAllSeats() {
        List<SeatDTO> dtos = seatService.getAllSeats().stream().map(SeatMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of seats retrieved successfully", dtos));
    }

    @Operation(summary = "Get active seats", description = "Returns a list of all active seats")
    @ApiResponse(responseCode = "200", description = "List of active seats retrieved successfully")
    @GetMapping("/active")
    public ResponseEntity<ApiResponseDTO<List<SeatDTO>>> getActiveSeats() {
        List<SeatDTO> dtos = seatService.getActiveSeats().stream().map(SeatMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of active seats retrieved successfully", dtos));
    }

    @Operation(summary = "Get seats by screen", description = "Returns a list of seats for a specific screen")
    @ApiResponse(responseCode = "200", description = "List of screen's seats retrieved successfully")
    @GetMapping("/screen/{screenId}")
    public ResponseEntity<ApiResponseDTO<List<SeatDTO>>> getSeatsByScreen(
            @Parameter(description = "ID of the screen") @PathVariable Long screenId) {
        List<SeatDTO> dtos = seatService.getSeatsByScreen(screenId).stream().map(SeatMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of screen's seats retrieved successfully", dtos));
    }

    @Operation(summary = "Check if seat exists", description = "Checks if a seat number is already taken in a screen")
    @ApiResponse(responseCode = "200", description = "Seat check completed successfully")
    @GetMapping("/check")
    public ResponseEntity<ApiResponseDTO<Boolean>> checkSeatExists(
            @Parameter(description = "Seat number to check") @RequestParam String seatNumber,
            @Parameter(description = "ID of the screen") @RequestParam Long screenId) {
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Seat check completed successfully", seatService.existsByNumberAndScreen(seatNumber, screenId)));
    }
}