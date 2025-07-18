package com.example.movieticketbookingbe.controller;
import com.example.movieticketbookingbe.dto.theaterowner.TheaterOwnerCreateDTO;
import com.example.movieticketbookingbe.dto.theaterowner.TheaterOwnerDTO;
import com.example.movieticketbookingbe.dto.theaterowner.TheaterOwnerPatchDTO;
import com.example.movieticketbookingbe.dto.theaterowner.TheaterOwnerWithUserCreateDTO;
import com.example.movieticketbookingbe.mapper.TheaterOwnerMapper;
import com.example.movieticketbookingbe.model.TheaterOwner;
import com.example.movieticketbookingbe.service.TheaterOwnerService;
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
import org.springframework.beans.factory.annotation.Autowired;
import com.example.movieticketbookingbe.dto.ApiResponseDTO;

@RestController
@RequestMapping("/api/theater-owner")
@RequiredArgsConstructor
@Tag(name = "TheaterOwner", description = "Theater owner management APIs")
public class TheaterOwnerController {
    private final TheaterOwnerService theaterOwnerService;

    @Autowired
    private TheaterOwnerService theaterOwnerServiceImpl;

    @Operation(summary = "Create a new theater owner", description = "Creates a new theater owner with the provided information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Theater owner created successfully", content = @Content(schema = @Schema(implementation = TheaterOwner.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDTO<TheaterOwnerDTO>> createTheaterOwner(@RequestBody TheaterOwnerCreateDTO createDTO) {
        TheaterOwner owner = TheaterOwnerMapper.toEntity(createDTO);
        TheaterOwnerDTO dto = TheaterOwnerMapper.toDTO(theaterOwnerService.createTheaterOwner(owner));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Theater owner created successfully", dto));
    }

    @Operation(summary = "Update a theater owner", description = "Partially updates an existing theater owner by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Theater owner updated successfully"),
            @ApiResponse(responseCode = "404", description = "Theater owner not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TheaterOwnerDTO>> patchTheaterOwner(
            @Parameter(description = "ID of the theater owner to update") @PathVariable Long id,
            @RequestBody TheaterOwnerPatchDTO patchDTO) {
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Theater owner updated successfully", TheaterOwnerMapper.toDTO(theaterOwnerService.patchTheaterOwner(id, patchDTO))));
    }

    @Operation(summary = "Delete a theater owner", description = "Deletes a theater owner by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Theater owner deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Theater owner not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteTheaterOwner(
            @Parameter(description = "ID of the theater owner to delete") @PathVariable Long id) {
        theaterOwnerService.deleteTheaterOwner(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Theater owner deleted successfully", null));
    }

    @Operation(summary = "Get a theater owner by ID", description = "Returns a theater owner by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Theater owner found", content = @Content(schema = @Schema(implementation = TheaterOwner.class))),
            @ApiResponse(responseCode = "404", description = "Theater owner not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TheaterOwnerDTO>> getTheaterOwnerById(
            @Parameter(description = "ID of the theater owner to retrieve") @PathVariable Long id) {
        return theaterOwnerService.getTheaterOwnerById(id)
                .map(owner -> ResponseEntity.ok(new ApiResponseDTO<>(200, "Theater owner found", TheaterOwnerMapper.toDTO(owner))))
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/search/{email}")
    public ResponseEntity<ApiResponseDTO<TheaterOwnerDTO>> getTheaterOwnerByEmail(
            @Parameter(description = "Email of the theater owner to retrieve") @PathVariable String email) {
        return theaterOwnerService.getTheaterOwnerByEmail(email)
                .map(owner -> ResponseEntity.ok(new ApiResponseDTO<>(200, "Theater owner found", TheaterOwnerMapper.toDTO(owner))))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all theater owners", description = "Returns a list of all theater owners")
    @ApiResponse(responseCode = "200", description = "List of theater owners retrieved successfully")
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<TheaterOwnerDTO>>> getAllTheaterOwners() {
        List<TheaterOwnerDTO> dtoList = theaterOwnerService.getAllTheaterOwners().stream().map(TheaterOwnerMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of theater owners retrieved successfully", dtoList));
    }

    @Operation(summary = "Get active theater owners", description = "Returns a list of all active theater owners")
    @ApiResponse(responseCode = "200", description = "List of active theater owners retrieved successfully")
    @GetMapping("/active")
    public ResponseEntity<ApiResponseDTO<List<TheaterOwnerDTO>>> getActiveTheaterOwners() {
        List<TheaterOwnerDTO> dtoList = theaterOwnerService.getActiveTheaterOwners().stream().map(TheaterOwnerMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of active theater owners retrieved successfully", dtoList));
    }

    @Operation(summary = "Get theater owner by user", description = "Returns a theater owner associated with a specific user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Theater owner found", content = @Content(schema = @Schema(implementation = TheaterOwner.class))),
            @ApiResponse(responseCode = "404", description = "Theater owner not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDTO<TheaterOwnerDTO>> getTheaterOwnerByUser(
            @Parameter(description = "ID of the user") @PathVariable Long userId) {
        return theaterOwnerService.getTheaterOwnerByUser(userId)
                .map(owner -> ResponseEntity.ok(new ApiResponseDTO<>(200, "Theater owner found", TheaterOwnerMapper.toDTO(owner))))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/with-user")
    public TheaterOwnerDTO createTheaterOwnerWithUser(@RequestBody TheaterOwnerWithUserCreateDTO dto) {
        return theaterOwnerServiceImpl.createTheaterOwnerWithUser(dto);
    }
} 