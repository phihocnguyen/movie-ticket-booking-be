package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.TheaterFoodInventory;
import com.example.movieticketbookingbe.model.Theater;
import com.example.movieticketbookingbe.service.TheaterFoodInventoryService;
import com.example.movieticketbookingbe.dto.theaterfoodinventory.TheaterFoodInventoryDTO;
import com.example.movieticketbookingbe.dto.theaterfoodinventory.TheaterFoodInventoryCreateDTO;
import com.example.movieticketbookingbe.dto.theaterfoodinventory.TheaterFoodInventoryPatchDTO;
import com.example.movieticketbookingbe.mapper.TheaterFoodInventoryMapper;
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
@RequestMapping("/api/theater-food")
@RequiredArgsConstructor
@Tag(name = "Theater Food", description = "Theater food management APIs")
public class TheaterFoodInventoryController {
    private final TheaterFoodInventoryService theaterFoodInventoryService;

    @Operation(summary = "Add food to theater", description = "Adds a new food item to a theater's inventory")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Food added successfully", content = @Content(schema = @Schema(implementation = TheaterFoodInventory.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDTO<TheaterFoodInventoryDTO>> addFoodToTheater(@RequestBody TheaterFoodInventoryCreateDTO foodCreateDTO) {
        TheaterFoodInventory food = TheaterFoodInventoryMapper.toEntity(foodCreateDTO);
        TheaterFoodInventoryDTO dto = TheaterFoodInventoryMapper.toDTO(theaterFoodInventoryService.createTheaterFoodInventory(food));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Food added successfully", dto));
    }

    @Operation(summary = "Update theater food", description = "Partially updates an existing food item in theater's inventory")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Food updated successfully"),
            @ApiResponse(responseCode = "404", description = "Food not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TheaterFoodInventoryDTO>> patchTheaterFood(
            @Parameter(description = "ID of the theater food to update") @PathVariable Long id,
            @RequestBody TheaterFoodInventoryPatchDTO patchDTO) {
        TheaterFoodInventory updated = theaterFoodInventoryService.patchTheaterFoodInventory(id, patchDTO);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Food updated successfully", TheaterFoodInventoryMapper.toDTO(updated)));
    }

    @Operation(summary = "Remove food from theater", description = "Removes a food item from theater's inventory")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Food removed successfully"),
            @ApiResponse(responseCode = "404", description = "Food not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> removeFoodFromTheater(
            @Parameter(description = "ID of the theater food to remove") @PathVariable Long id) {
        theaterFoodInventoryService.deleteTheaterFoodInventory(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Food removed successfully", null));
    }

    @Operation(summary = "Get theater food by ID", description = "Returns a specific food item from theater's inventory")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Food found", content = @Content(schema = @Schema(implementation = TheaterFoodInventory.class))),
            @ApiResponse(responseCode = "404", description = "Food not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TheaterFoodInventory>> getTheaterFoodById(
            @Parameter(description = "ID of the theater food to retrieve") @PathVariable Long id) {
        return theaterFoodInventoryService.getTheaterFoodInventoryById(id)
                .map(food -> ResponseEntity.ok(new ApiResponseDTO<>(200, "Food found", food)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all theater foods", description = "Returns all food items from all theaters")
    @ApiResponse(responseCode = "200", description = "List of all theater foods retrieved successfully")
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<TheaterFoodInventory>>> getAllTheaterFoods() {
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of all theater foods retrieved successfully", theaterFoodInventoryService.getAllTheaterFoodInventory()));
    }

    @Operation(summary = "Get active theater foods", description = "Returns all active food items from all theaters")
    @ApiResponse(responseCode = "200", description = "List of active theater foods retrieved successfully")
    @GetMapping("/active")
    public ResponseEntity<ApiResponseDTO<List<TheaterFoodInventoryDTO>>> getActiveTheaterFoods() {
        List<TheaterFoodInventoryDTO> dtos = theaterFoodInventoryService.getActiveTheaterFoodInventory().stream().map(TheaterFoodInventoryMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of active theater foods retrieved successfully", dtos));
    }

    @Operation(summary = "Get theater foods by theater", description = "Returns all food items for a specific theater")
    @ApiResponse(responseCode = "200", description = "List of theater foods retrieved successfully")
    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<ApiResponseDTO<List<TheaterFoodInventoryDTO>>> getTheaterFoodsByTheater(
            @Parameter(description = "ID of the theater") @PathVariable Long theaterId) {
        List<TheaterFoodInventoryDTO> dtos = theaterFoodInventoryService.getTheaterFoodInventoryByTheater(theaterId).stream().map(TheaterFoodInventoryMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of theater foods retrieved successfully", dtos));
    }

    @Operation(summary = "Check if food exists in theater", description = "Checks if a food item exists in a theater's inventory")
    @ApiResponse(responseCode = "200", description = "Check completed successfully")
    @GetMapping("/check")
    public ResponseEntity<ApiResponseDTO<Boolean>> checkFoodExistsInTheater(
            @Parameter(description = "ID of the theater") @RequestParam Long theaterId,
            @Parameter(description = "Name of the food item") @RequestParam String name) {
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Check completed successfully", theaterFoodInventoryService.existsByTheaterAndName(theaterId, name)));
    }
}