package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.dto.auth.AuthResponse;
import com.example.movieticketbookingbe.dto.auth.LoginRequest;
import com.example.movieticketbookingbe.dto.auth.RegisterRequest;
import com.example.movieticketbookingbe.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication APIs")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Login user", description = "Authenticate user and return JWT token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Register new user", description = "Register a new user and return JWT token")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}