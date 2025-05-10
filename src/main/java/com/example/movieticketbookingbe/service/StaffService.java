package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.model.Staff;
import java.util.List;
import java.util.Optional;

public interface StaffService {
    Staff createStaff(Staff staff);

    Staff updateStaff(Long id, Staff staff);

    void deleteStaff(Long id);

    Optional<Staff> getStaffById(Long id);

    List<Staff> getAllStaff();

    List<Staff> getActiveStaff();

    List<Staff> getStaffByTheater(Long theaterId);

    Optional<Staff> getStaffByUser(Long userId);

    boolean existsByUser(Long userId);

    boolean existsByTheaterAndPosition(Long theaterId, String position);
}