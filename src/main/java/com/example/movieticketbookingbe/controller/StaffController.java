package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.dto.StaffDTO;
import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import com.example.movieticketbookingbe.mapper.StaffMapper;
import com.example.movieticketbookingbe.model.Staff;
import com.example.movieticketbookingbe.model.User;
import com.example.movieticketbookingbe.service.StaffService;
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
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@Tag(name = "Staff", description = "Staff management APIs")
public class StaffController {
    private final StaffService staffService;

    @Operation(summary = "Create a new staff member", description = "Creates a new staff member with the provided information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff member created successfully", content = @Content(schema = @Schema(implementation = Staff.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Staff> createStaff(@RequestBody Staff staff) {
        return ResponseEntity.ok(staffService.createStaff(staff));
    }

    @Operation(summary = "Update a staff member", description = "Updates an existing staff member by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff member updated successfully"),
            @ApiResponse(responseCode = "404", description = "Staff member not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(
            @Parameter(description = "ID of the staff member to update") @PathVariable Long id,
            @RequestBody Staff staff) {
        return ResponseEntity.ok(staffService.updateStaff(id, staff));
    }

    @Operation(summary = "Delete a staff member", description = "Deletes a staff member by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff member deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Staff member not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(
            @Parameter(description = "ID of the staff member to delete") @PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get a staff member by ID", description = "Returns a staff member by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff member found", content = @Content(schema = @Schema(implementation = Staff.class))),
            @ApiResponse(responseCode = "404", description = "Staff member not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(
            @Parameter(description = "ID of the staff member to retrieve") @PathVariable Long id) {
        return staffService.getStaffById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all staff members", description = "Returns a list of all staff members")
    @ApiResponse(responseCode = "200", description = "List of staff members retrieved successfully")
    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff() {
        return ResponseEntity.ok(staffService.getAllStaff());
    }

    @Operation(summary = "Get active staff members", description = "Returns a list of all active staff members")
    @ApiResponse(responseCode = "200", description = "List of active staff members retrieved successfully")
    @GetMapping("/active")
    public ResponseEntity<ApiResponseDTO<List<StaffDTO>>> getActiveStaff() {
        List<Staff> staffList = staffService.findByUserRoleAndUserIsActiveTrue(User.UserRole.THEATER_OWNER);
        List<StaffDTO> dtoList = staffList.stream()
                .map(StaffMapper::toDTO)
                .toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of active staff members retrieved successfully", dtoList));
    }

    @Operation(summary = "Get staff members by theater", description = "Returns a list of staff members for a specific theater")
    @ApiResponse(responseCode = "200", description = "List of theater's staff members retrieved successfully")
    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<ApiResponseDTO<List<StaffDTO>>> getStaffByTheater(
            @Parameter(description = "ID of the theater") @PathVariable Long theaterId) {
        List<StaffDTO> dtoList = staffService.getStaffByTheater(theaterId).stream().map(StaffMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of theater's staff members retrieved successfully", dtoList));
    }

    @Operation(summary = "Get staff member by user", description = "Returns a staff member associated with a specific user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff member found", content = @Content(schema = @Schema(implementation = Staff.class))),
            @ApiResponse(responseCode = "404", description = "Staff member not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDTO<StaffDTO>> getStaffByUser(
            @Parameter(description = "ID of the user") @PathVariable Long userId) {
        return staffService.getStaffByUser(userId)
                .map(staff -> ResponseEntity.ok(new ApiResponseDTO<>(200, "Staff member found", StaffMapper.toDTO(staff))))
                .orElse(ResponseEntity.ok(new ApiResponseDTO<>(404, "Staff member not found", null)));
    }
}