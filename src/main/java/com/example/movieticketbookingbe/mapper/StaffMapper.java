package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.StaffDTO;
import com.example.movieticketbookingbe.model.Staff;

public class StaffMapper {
    public static StaffDTO toDTO(Staff staff) {
        if (staff == null) return null;
        StaffDTO dto = new StaffDTO();
        dto.setId(staff.getId());
        dto.setTheaterId(staff.getTheater() != null ? staff.getTheater().getId() : null);
        dto.setUserId(staff.getUser() != null ? staff.getUser().getId() : null);
        dto.setPosition(staff.getPosition());
        dto.setSalary(staff.getSalary());
        dto.setIsActive(staff.getIsActive());
        dto.setCreatedAt(staff.getCreatedAt());
        dto.setUpdatedAt(staff.getUpdatedAt());
        return dto;
    }
}
