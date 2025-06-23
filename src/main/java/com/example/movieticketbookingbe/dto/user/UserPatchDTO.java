package com.example.movieticketbookingbe.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserPatchDTO {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private Boolean isActive;
    private String role;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
} 