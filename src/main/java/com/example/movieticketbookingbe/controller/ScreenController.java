package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.Screen;
import com.example.movieticketbookingbe.service.ScreenService;
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
    public ResponseEntity<Screen> createScreen(@RequestBody Screen screen) {
        return ResponseEntity.ok(screenService.createScreen(screen));
    }

    @Operation(summary = "Update a screen", description = "Updates an existing screen by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Screen updated successfully"),
            @ApiResponse(responseCode = "404", description = "Screen not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Screen> updateScreen(
            @Parameter(description = "ID of the screen to update") @PathVariable Long id,
            @RequestBody Screen screen) {
        return ResponseEntity.ok(screenService.updateScreen(id, screen));
    }

    @Operation(summary = "Delete a screen", description = "Deletes a screen by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Screen deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Screen not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScreen(
            @Parameter(description = "ID of the screen to delete") @PathVariable Long id) {
        screenService.deleteScreen(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get a screen by ID", description = "Returns a screen by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Screen found", content = @Content(schema = @Schema(implementation = Screen.class))),
            @ApiResponse(responseCode = "404", description = "Screen not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Screen> getScreenById(
            @Parameter(description = "ID of the screen to retrieve") @PathVariable Long id) {
        return screenService.getScreenById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all screens", description = "Returns a list of all screens")
    @ApiResponse(responseCode = "200", description = "List of screens retrieved successfully")
    @GetMapping
    public ResponseEntity<List<Screen>> getAllScreens() {
        return ResponseEntity.ok(screenService.getAllScreens());
    }

    @Operation(summary = "Get active screens", description = "Returns a list of all active screens")
    @ApiResponse(responseCode = "200", description = "List of active screens retrieved successfully")
    @GetMapping("/active")
    public ResponseEntity<List<Screen>> getActiveScreens() {
        return ResponseEntity.ok(screenService.getActiveScreens());
    }

    @Operation(summary = "Get screens by theater", description = "Returns a list of screens for a specific theater")
    @ApiResponse(responseCode = "200", description = "List of theater's screens retrieved successfully")
    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<Screen>> getScreensByTheater(
            @Parameter(description = "ID of the theater") @PathVariable Long theaterId) {
        return ResponseEntity.ok(screenService.getScreensByTheater(theaterId));
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