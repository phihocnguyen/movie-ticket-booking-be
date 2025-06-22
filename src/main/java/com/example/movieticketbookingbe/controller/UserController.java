package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.dto.user.UserDTO;
import com.example.movieticketbookingbe.service.UserService;
import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import com.example.movieticketbookingbe.mapper.UserMapper;
import com.example.movieticketbookingbe.dto.user.UserCreateDTO;
import com.example.movieticketbookingbe.dto.user.UserPatchDTO;
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
import com.example.movieticketbookingbe.model.User;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management APIs")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Create a new user", description = "Creates a new user with the provided information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User created successfully", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/create")
    public ResponseEntity<ApiResponseDTO<UserDTO>> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        User user = UserMapper.toEntity(userCreateDTO);
        UserDTO dto = UserMapper.toDTO(userService.createUser(user));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "User created successfully", dto));
    }

    @Operation(summary = "Update a user", description = "Partially updates an existing user by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserDTO>> patchUser(
            @Parameter(description = "ID of the user to update") @PathVariable Long id,
            @RequestBody UserPatchDTO patchDTO) {
        UserDTO dto = UserMapper.toDTO(userService.patchUser(id, patchDTO));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "User updated successfully", dto));
    }

    @Operation(summary = "Delete a user", description = "Deletes a user by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteUser(
            @Parameter(description = "ID of the user to delete") @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Xóa người dùng thành công", null));
    }

    @Operation(summary = "Get a user by ID", description = "Returns a user by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserDTO>> getUserById(
            @Parameter(description = "ID of the user to retrieve") @PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(new ApiResponseDTO<>(200, "Tìm thấy người dùng", UserMapper.toDTO(user))))
                .orElse(ResponseEntity.ok(new ApiResponseDTO<>(404, "Không tìm thấy người dùng", null)));
    }


    @Operation(summary = "Get all customer", description = "Returns a list of all active customers")
    @ApiResponse(responseCode = "200", description = "List of active customers retrieved successfully")
    @GetMapping("/getAllCustomer")
    public ResponseEntity<ApiResponseDTO<List<UserDTO>>> getActiveUsers() {
        List<User> staffList = userService.getAllCustomers(User.UserRole.CUSTOMER);
        List<UserDTO> dtoList = staffList.stream()
                .map(UserMapper::toDTO)
                .toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of active customers retrieved successfully", dtoList));
    }

    @Operation(summary = "Check if email exists", description = "Checks if an email address is already registered")
    @ApiResponse(responseCode = "200", description = "Email check completed successfully")
    @GetMapping("/check-email")
    public ResponseEntity<ApiResponseDTO<Boolean>> checkEmailExists(
            @Parameter(description = "Email address to check") @RequestParam String email) {
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Email check completed successfully", userService.existsByEmail(email)));
    }

    @Operation(summary = "Check if phone number exists", description = "Checks if a phone number is already registered")
    @ApiResponse(responseCode = "200", description = "Phone number check completed successfully")
    @GetMapping("/check-phone")
    public ResponseEntity<ApiResponseDTO<Boolean>> checkPhoneExists(
            @Parameter(description = "Phone number to check") @RequestParam String phoneNumber) {
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Phone number check completed successfully", userService.existsByPhoneNumber(phoneNumber)));
    }

    @Operation(summary = "Check if username exists", description = "Checks if a username is already taken")
    @ApiResponse(responseCode = "200", description = "Username check completed successfully")
    @GetMapping("/check-username")
    public ResponseEntity<ApiResponseDTO<Boolean>> checkUsernameExists(
            @Parameter(description = "Username to check") @RequestParam String username) {
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Username check completed successfully", userService.existsByUsername(username)));
    }
}