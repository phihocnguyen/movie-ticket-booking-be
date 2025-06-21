package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.Theater;
import com.example.movieticketbookingbe.service.TheaterService;
import com.example.movieticketbookingbe.dto.TheaterDTO;
import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import com.example.movieticketbookingbe.mapper.TheaterMapper;
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
@RequestMapping("/api/theaters")
@RequiredArgsConstructor
@Tag(name = "Theater", description = "Theater management APIs")
public class TheaterController {
    private final TheaterService theaterService;

    @Operation(summary = "Create a new theater", description = "Creates a new theater with the provided information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Theater created successfully", content = @Content(schema = @Schema(implementation = Theater.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Theater> createTheater(@RequestBody Theater theater) {
        return ResponseEntity.ok(theaterService.createTheater(theater));
    }

    @Operation(summary = "Update a theater", description = "Updates an existing theater by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Theater updated successfully"),
            @ApiResponse(responseCode = "404", description = "Theater not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Theater> updateTheater(
            @Parameter(description = "ID of the theater to update") @PathVariable Long id,
            @RequestBody Theater theater) {
        return ResponseEntity.ok(theaterService.updateTheater(id, theater));
    }

    @Operation(summary = "Delete a theater", description = "Deletes a theater by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Theater deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Theater not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheater(
            @Parameter(description = "ID of the theater to delete") @PathVariable Long id) {
        theaterService.deleteTheater(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get a theater by ID", description = "Returns a theater by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Theater found", content = @Content(schema = @Schema(implementation = Theater.class))),
            @ApiResponse(responseCode = "404", description = "Theater not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TheaterDTO>> getTheaterById(
            @Parameter(description = "ID of the theater to retrieve") @PathVariable Long id) {
        return theaterService.getTheaterById(id)
                .map(theater -> ResponseEntity.ok(new ApiResponseDTO<>(200, "Theater found", TheaterMapper.toDTO(theater))))
                .orElse(ResponseEntity.ok(new ApiResponseDTO<>(404, "Theater not found", null)));
    }

    @Operation(summary = "Get all theaters", description = "Returns a list of all theaters")
    @ApiResponse(responseCode = "200", description = "List of theaters retrieved successfully")
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<TheaterDTO>>> getAllTheaters() {
        List<TheaterDTO> dtos = theaterService.getAllTheaters().stream().map(TheaterMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of theaters retrieved successfully", dtos));
    }

    @Operation(summary = "Get active theaters", description = "Returns a list of all active theaters")
    @ApiResponse(responseCode = "200", description = "List of active theaters retrieved successfully")
    @GetMapping("/active")
    public ResponseEntity<ApiResponseDTO<List<TheaterDTO>>> getActiveTheaters() {
        List<TheaterDTO> dtos = theaterService.getActiveTheaters().stream().map(TheaterMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of active theaters retrieved successfully", dtos));
    }

    @Operation(summary = "Search theaters", description = "Search theaters by name, city, and state")
    @ApiResponse(responseCode = "200", description = "Search results retrieved successfully")
    @GetMapping("/search")
    public ResponseEntity<List<Theater>> searchTheaters(
            @Parameter(description = "Theater name to search for") @RequestParam(required = false) String name,
            @Parameter(description = "City to filter by") @RequestParam(required = false) String city,
            @Parameter(description = "State to filter by") @RequestParam(required = false) String state) {
        return ResponseEntity.ok(theaterService.searchTheaters(name, city, state));
    }

    @Operation(summary = "Check if theater name exists", description = "Checks if a theater name is already taken")
    @ApiResponse(responseCode = "200", description = "Name check completed successfully")
    @GetMapping("/check-name")
    public ResponseEntity<Boolean> checkNameExists(
            @Parameter(description = "Theater name to check") @RequestParam String name) {
        return ResponseEntity.ok(theaterService.existsByName(name));
    }

    @Operation(summary = "Check if theater email exists", description = "Checks if a theater email is already registered")
    @ApiResponse(responseCode = "200", description = "Email check completed successfully")
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(
            @Parameter(description = "Theater email to check") @RequestParam String email) {
        return ResponseEntity.ok(theaterService.existsByEmail(email));
    }

    @Operation(summary = "Check if theater phone exists", description = "Checks if a theater phone number is already registered")
    @ApiResponse(responseCode = "200", description = "Phone number check completed successfully")
    @GetMapping("/check-phone")
    public ResponseEntity<Boolean> checkPhoneExists(
            @Parameter(description = "Theater phone number to check") @RequestParam String phoneNumber) {
        return ResponseEntity.ok(theaterService.existsByPhoneNumber(phoneNumber));
    }
}