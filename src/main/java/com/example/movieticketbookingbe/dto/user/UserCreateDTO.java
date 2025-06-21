package com.example.movieticketbookingbe.dto.user;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UserCreateDTO {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;
    private String username;
    private String fullName;
    private LocalDate dateOfBirth;
} 