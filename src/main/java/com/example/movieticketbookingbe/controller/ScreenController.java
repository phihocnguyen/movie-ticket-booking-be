package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.Screen;
import com.example.movieticketbookingbe.service.ScreenService;
import com.example.movieticketbookingbe.dto.ScreenDTO;
import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import com.example.movieticketbookingbe.mapper.ScreenMapper;
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
@RequestMapping("/api/screens")
@RequiredArgsConstructor
@Tag(name = "Screen", description = "Screen management APIs")
public class ScreenController {
    private final ScreenService screenService;

    @Operation(summary = "Create a new screen", description = "Creates a new screen with the provided information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Screen created successfully", content = @Content(schema = @Schema(implementation = Screen.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDTO<ScreenDTO>> createScreen(@RequestBody Screen screen) {
        ScreenDTO dto = ScreenMapper.toDTO(screenService.createScreen(screen));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Screen created successfully", dto));
    }

    @Operation(summary = "Update a screen", description = "Updates an existing screen by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Screen updated successfully"),
            @ApiResponse(responseCode = "404", description = "Screen not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ScreenDTO>> updateScreen(
            @Parameter(description = "ID of the screen to update") @PathVariable Long id,
            @RequestBody Screen screen) {
        ScreenDTO dto = ScreenMapper.toDTO(screenService.updateScreen(id, screen));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Screen updated successfully", dto));
    }

    @Operation(summary = "Delete a screen", description = "Deletes a screen by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Screen deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Screen not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteScreen(
            @Parameter(description = "ID of the screen to delete") @PathVariable Long id) {
        screenService.deleteScreen(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Screen deleted successfully", null));
    }

    @Operation(summary = "Get a screen by ID", description = "Returns a screen by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Screen found", content = @Content(schema = @Schema(implementation = Screen.class))),
            @ApiResponse(responseCode = "404", description = "Screen not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ScreenDTO>> getScreenById(
            @Parameter(description = "ID of the screen to retrieve") @PathVariable Long id) {
        return screenService.getScreenById(id)
                .map(screen -> ResponseEntity.ok(new ApiResponseDTO<>(200, "Screen found", ScreenMapper.toDTO(screen))))
                .orElse(ResponseEntity.ok(new ApiResponseDTO<>(404, "Screen not found", null)));
    }

    @Operation(summary = "Get all screens", description = "Returns a list of all screens")
    @ApiResponse(responseCode = "200", description = "List of screens retrieved successfully")
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ScreenDTO>>> getAllScreens() {
        List<ScreenDTO> dtos = screenService.getAllScreens().stream().map(ScreenMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of screens retrieved successfully", dtos));
    }

    @Operation(summary = "Get active screens", description = "Returns a list of all active screens")
    @ApiResponse(responseCode = "200", description = "List of active screens retrieved successfully")
    @GetMapping("/active")
    public ResponseEntity<ApiResponseDTO<List<ScreenDTO>>> getActiveScreens() {
        List<ScreenDTO> dtos = screenService.getActiveScreens().stream().map(ScreenMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of active screens retrieved successfully", dtos));
    }

    @Operation(summary = "Get screens by theater", description = "Returns a list of screens for a specific theater")
    @ApiResponse(responseCode = "200", description = "List of theater's screens retrieved successfully")
    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<ApiResponseDTO<List<ScreenDTO>>> getScreensByTheater(
            @Parameter(description = "ID of the theater") @PathVariable Long theaterId) {
        List<ScreenDTO> dtos = screenService.getScreensByTheater(theaterId).stream().map(ScreenMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of theater's screens retrieved successfully", dtos));
    }

    @Operation(summary = "Check if screen name exists", description = "Checks if a screen name is already taken in a theater")
    @ApiResponse(responseCode = "200", description = "Screen name check completed successfully")
    @GetMapping("/check-name")
    public ResponseEntity<Boolean> checkNameExists(
            @Parameter(description = "Screen name to check") @RequestParam String screenName,
            @Parameter(description = "ID of the theater") @RequestParam Long theaterId) {
        return ResponseEntity.ok(screenService.existsByScreenNameAndTheater(screenName, theaterId));
    }
}