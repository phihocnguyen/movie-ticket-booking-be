package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.Staff;
import com.example.movieticketbookingbe.repository.StaffRepository;
import com.example.movieticketbookingbe.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;

    @Override
    public Staff createStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public Staff updateStaff(Long id, Staff staff) {
        Staff existingStaff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        staff.setId(existingStaff.getId());
        return staffRepository.save(staff);
    }

    @Override
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Staff> getStaffById(Long id) {
        return staffRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Staff> getActiveStaff() {
        return staffRepository.findByIsActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Staff> getStaffByTheater(Long theaterId) {
        return staffRepository.findByTheaterId(theaterId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Staff> getStaffByUser(Long userId) {
        return staffRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUser(Long userId) {
        return staffRepository.existsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByTheaterAndPosition(Long theaterId, String position) {
        return staffRepository.existsByTheaterIdAndPosition(theaterId, position);
    }
}