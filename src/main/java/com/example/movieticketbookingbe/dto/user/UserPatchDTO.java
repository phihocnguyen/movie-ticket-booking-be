package com.example.movieticketbookingbe.dto.user;

import lombok.Data;

@Data
public class UserPatchDTO {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private Boolean isActive;
    private String role;
} 