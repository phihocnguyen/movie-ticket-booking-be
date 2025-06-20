package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.response.StaffResponseDTO;
import java.time.format.DateTimeFormatter;
public class StaffMapper {
    public static StaffResponseDTO toDTO(com.example.movieticketbookingbe.model.Staff staff) {
        var user = staff.getUser();
        return new StaffResponseDTO(
                staff.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getUsername(),
                user.getFullName(),
                user.getDateOfBirth() != null
                        ? user.getDateOfBirth().format(DateTimeFormatter.ISO_DATE)
                        : null
        );
    }
}
