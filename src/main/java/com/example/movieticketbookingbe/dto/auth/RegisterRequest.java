package com.example.movieticketbookingbe.dto.auth;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String fullName;
    private LocalDate dateOfBirth;
}