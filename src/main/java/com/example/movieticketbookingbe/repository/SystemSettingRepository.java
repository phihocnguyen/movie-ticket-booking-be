package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.dto.systemsetting.SystemSettingDTO;
import com.example.movieticketbookingbe.model.SystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemSettingRepository extends JpaRepository<SystemSetting, Long> {

    // Setting của 1 owner cụ thể
    Optional<SystemSetting> findByOwner_Id(Long ownerId);

    // Setting toàn hệ thống (owner = null)
    Optional<SystemSetting> findByOwnerIsNull();

    // Kiểm tra owner đã có setting chưa
    boolean existsByOwner_Id(Long ownerId);

    boolean existsByOwnerIsNull();
}
