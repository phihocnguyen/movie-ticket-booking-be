package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.dto.auth.AuthResponse;
import com.example.movieticketbookingbe.dto.auth.LoginRequest;
import com.example.movieticketbookingbe.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);

    AuthResponse register(RegisterRequest request);
}