package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.dto.systemsetting.SystemSettingCreateDTO;
import com.example.movieticketbookingbe.dto.systemsetting.SystemSettingDTO;
import com.example.movieticketbookingbe.dto.systemsetting.SystemSettingPatchDTO;
import com.example.movieticketbookingbe.model.SystemSetting;

public interface SystemSettingService {

    // Lấy đúng setting theo owner (không có thì trả lỗi)
    SystemSetting getSettingByOwnerId(Long ownerId);

    // Lấy setting global
    SystemSetting getGlobalSetting();


    // Cập nhật setting hiện có (theo ID hoặc theo owner)
    SystemSettingDTO  update(Long id, SystemSettingPatchDTO dto);

    // Xoá setting (không bắt buộc phải dùng)
    void delete(Long id);
    // Tạo setting mới nếu chưa có (admin hoặc theater owner)
    SystemSettingDTO create(SystemSettingPatchDTO dto, Long ownerId);
}
