package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.StaffDTO;
import com.example.movieticketbookingbe.dto.staff.StaffCreateDTO;
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

    public static Staff toEntity(StaffCreateDTO dto) {
        if (dto == null) return null;
        Staff staff = new Staff();
        staff.setTheater(null);
        staff.setUser(null);
        staff.setPosition(dto.getPosition());
        staff.setSalary(dto.getSalary());
        staff.setIsActive(dto.getIsActive());
        return staff;
    }
}
