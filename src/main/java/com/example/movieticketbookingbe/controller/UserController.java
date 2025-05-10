package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.User;
import com.example.movieticketbookingbe.service.UserService;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management APIs")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Create a new user", description = "Creates a new user with the provided information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User created successfully", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @Operation(summary = "Update a user", description = "Updates an existing user by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @Parameter(description = "ID of the user to update") @PathVariable Long id,
            @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @Operation(summary = "Delete a user", description = "Deletes a user by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to delete") @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get a user by ID", description = "Returns a user by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "ID of the user to retrieve") @PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all users", description = "Returns a list of all users")
    @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Get active users", description = "Returns a list of all active users")
    @ApiResponse(responseCode = "200", description = "List of active users retrieved successfully")
    @GetMapping("/active")
    public ResponseEntity<List<User>> getActiveUsers() {
        return ResponseEntity.ok(userService.getActiveUsers());
    }

    @Operation(summary = "Check if email exists", description = "Checks if an email address is already registered")
    @ApiResponse(responseCode = "200", description = "Email check completed successfully")
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(
            @Parameter(description = "Email address to check") @RequestParam String email) {
        return ResponseEntity.ok(userService.existsByEmail(email));
    }

    @Operation(summary = "Check if phone number exists", description = "Checks if a phone number is already registered")
    @ApiResponse(responseCode = "200", description = "Phone number check completed successfully")
    @GetMapping("/check-phone")
    public ResponseEntity<Boolean> checkPhoneExists(
            @Parameter(description = "Phone number to check") @RequestParam String phoneNumber) {
        return ResponseEntity.ok(userService.existsByPhoneNumber(phoneNumber));
    }

    @Operation(summary = "Check if username exists", description = "Checks if a username is already taken")
    @ApiResponse(responseCode = "200", description = "Username check completed successfully")
    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUsernameExists(
            @Parameter(description = "Username to check") @RequestParam String username) {
        return ResponseEntity.ok(userService.existsByUsername(username));
    }
}