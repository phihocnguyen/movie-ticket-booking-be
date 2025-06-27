package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.dto.voucher.VoucherDTO;
import com.example.movieticketbookingbe.mapper.VoucherMapper;
import com.example.movieticketbookingbe.model.Theater;
import com.example.movieticketbookingbe.model.Voucher;
import com.example.movieticketbookingbe.service.TheaterService;
import com.example.movieticketbookingbe.dto.theater.TheaterDTO;
import com.example.movieticketbookingbe.dto.theater.TheaterCreateDTO;
import com.example.movieticketbookingbe.dto.theater.TheaterPatchDTO;
import com.example.movieticketbookingbe.mapper.TheaterMapper;
import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponseDTO<TheaterDTO>> createTheater(@RequestBody TheaterCreateDTO theaterCreateDTO) {
        Theater theater = TheaterMapper.toEntity(theaterCreateDTO);
        TheaterDTO dto = TheaterMapper.toDTO(theaterService.createTheater(theater));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Created theater successfully", dto));
    }

    @Operation(summary = "Update a theater", description = "Partially updates an existing theater by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Theater updated successfully"),
            @ApiResponse(responseCode = "404", description = "Theater not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PatchMapping("/{id}")
    public ResponseEntity< ApiResponseDTO<TheaterDTO>> patchTheater(
            @Parameter(description = "ID of the theater to update") @PathVariable Long id,
            @RequestBody TheaterPatchDTO patchDTO) {
        TheaterDTO dto = TheaterMapper.toDTO(theaterService.patchTheater(id, patchDTO));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Updated theater successfully", dto));
    }

    @Operation(summary = "Delete a theater", description = "Deletes a theater by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Theater deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Theater not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteTheater(
            @Parameter(description = "ID of the theater to delete") @PathVariable Long id) {
        theaterService.deleteTheater(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Deleted theater successfully", null));
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
                .map(theater -> {
                    TheaterDTO dto = TheaterMapper.toDTO(theater);
                    ApiResponseDTO<TheaterDTO> response = new ApiResponseDTO<>(200, "Theater found", dto);
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponseDTO<>(404, "Theater not found", null)));
    }

    @Operation(summary = "Get all theaters", description = "Returns a list of all theaters")
    @ApiResponse(responseCode = "200", description = "List of theaters retrieved successfully")
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<TheaterDTO>>> getAllTheaters() {
        List<TheaterDTO> dtos = theaterService.getAllTheaters().stream().map(TheaterMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "get all theater successfully", dtos));
    }

    @Operation(summary = "Get active theaters", description = "Returns a list of all active theaters")
    @ApiResponse(responseCode = "200", description = "List of active theaters retrieved successfully")
    @GetMapping("/active")
    public ResponseEntity<ApiResponseDTO<List<TheaterDTO>>> getActiveTheaters() {
        List<TheaterDTO> dtos = theaterService.getActiveTheaters().stream().map(TheaterMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "get all active theater successfully", dtos));
    }

    @Operation(summary = "Search theaters", description = "Search theaters by name, city, and state")
    @ApiResponse(responseCode = "200", description = "Search results retrieved successfully")
    @GetMapping("/search")
    public ResponseEntity<ApiResponseDTO<List<Theater>>> searchTheaters(
            @Parameter(description = "Theater name to search for") @RequestParam(required = false) String name,
            @Parameter(description = "City to filter by") @RequestParam(required = false) String city,
            @Parameter(description = "State to filter by") @RequestParam(required = false) String state) {
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Theater found", theaterService.searchTheaters(name, city, state)));
    }

    @Operation(summary = "Check if theater address exists", description = "Checks if a theater address is already taken")
    @ApiResponse(responseCode = "200", description = "Address check completed successfully")
    @GetMapping("/check-address")
    public ResponseEntity<ApiResponseDTO<Boolean>> checkAddressExists(
            @RequestParam String address) {
        boolean exists = theaterService.existsByAddress(address.trim());
        return ResponseEntity.ok(new ApiResponseDTO<>(200,
                exists ? "Theater Address already exists" : "Theater Address is available", exists));
    }
    @Operation(summary = "Check if theater email exists", description = "Checks if a theater email is already registered")
    @ApiResponse(responseCode = "200", description = "Email check completed successfully")
    @GetMapping("/check-email")
    public ResponseEntity<ApiResponseDTO<Boolean>> checkEmailExists(
            @Parameter(description = "Theater email to check") @RequestParam String email) {
        boolean exists = theaterService.existsByEmail(email);
        String message = exists ? "Theater email already exists" : "Theater email is available";
        return ResponseEntity.ok(new ApiResponseDTO<>(200, message, exists));
    }

    @Operation(summary = "Check if theater phone exists", description = "Checks if a theater phone number is already registered")
    @ApiResponse(responseCode = "200", description = "Phone number check completed successfully")
    @GetMapping("/check-phone")
    public ResponseEntity<ApiResponseDTO<Boolean>> checkPhoneExists(
            @Parameter(description = "Theater phone number to check") @RequestParam String phoneNumber) {
        boolean exists = theaterService.existsByPhoneNumber(phoneNumber);
        String message = exists ? "Theater phoneNumber already exists" : "Theater phoneNumber is available";
        return ResponseEntity.ok(new ApiResponseDTO<>(200, message, exists));
    }

    @Operation(summary = "Get theaters by theater owner ID", description = "Returns a list of theaters by theater owner ID")
    @ApiResponse(responseCode = "200", description = "List of theaters retrieved successfully")
    @GetMapping("/theater-owner")
    public ResponseEntity<ApiResponseDTO<List<TheaterDTO>>> getTheatersByTheaterOwnerId(@RequestParam Long theaterOwnerId) {
        List<TheaterDTO> dtos = theaterService.getTheatersByTheaterOwnerId(theaterOwnerId).stream().map(TheaterMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Theaters by owner", dtos));
    }
}