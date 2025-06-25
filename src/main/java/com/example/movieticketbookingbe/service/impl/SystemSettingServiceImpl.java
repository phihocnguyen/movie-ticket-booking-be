package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.dto.systemsetting.SystemSettingCreateDTO;
import com.example.movieticketbookingbe.dto.systemsetting.SystemSettingDTO;
import com.example.movieticketbookingbe.dto.systemsetting.SystemSettingPatchDTO;
import com.example.movieticketbookingbe.mapper.SystemSettingMapper;
import com.example.movieticketbookingbe.model.SystemSetting;
import com.example.movieticketbookingbe.model.TheaterOwner;
import com.example.movieticketbookingbe.repository.SystemSettingRepository;
import com.example.movieticketbookingbe.repository.TheaterOwnerRepository;
import com.example.movieticketbookingbe.service.SystemSettingService;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SystemSettingServiceImpl implements SystemSettingService {

    private final SystemSettingRepository settingRepository;
    private final TheaterOwnerRepository ownerRepository;
    private final TheaterOwnerRepository theaterOwnerRepository;


    @Override
    public SystemSetting getSettingByOwnerId(Long ownerId) {
        return settingRepository.findByOwner_Id(ownerId).orElse(null);
    }

    @Override
    public SystemSetting getGlobalSetting() {
        return settingRepository.findByOwnerIsNull().orElse(null);
    }
    @Override
    public SystemSettingDTO create(SystemSettingPatchDTO dto, Long ownerId) {
        if (ownerId == null) {
            if (settingRepository.existsByOwnerIsNull()) {
                throw new IllegalArgumentException("Admin đã có setting");
            }
        } else {
            if (settingRepository.existsByOwner_Id(ownerId)) {
                throw new IllegalArgumentException("Owner đã có setting");
            }
        }

        SystemSetting setting = new SystemSetting();
        if (ownerId != null) {
            TheaterOwner owner = theaterOwnerRepository.findById(ownerId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy owner"));
            setting.setOwner(owner);
        }

        SystemSettingMapper.updateEntityFromDTO(setting, dto);
        setting.setCreatedAt(LocalDateTime.now());
        setting.setUpdatedAt(LocalDateTime.now());

        return SystemSettingMapper.toResponseDTO(settingRepository.save(setting));
    }
    @Override
    public SystemSettingDTO update( Long ownerId,SystemSettingPatchDTO dto) {
        SystemSetting setting;

        if (ownerId == null) {
            setting = settingRepository.findByOwnerIsNull()
                    .orElseThrow(() -> new IllegalArgumentException("Chưa có setting cho admin"));
        } else {
            setting = settingRepository.findByOwner_Id(ownerId)
                    .orElseThrow(() -> new IllegalArgumentException("Chưa có setting cho ownerId = " + ownerId));
        }

        SystemSettingMapper.updateEntityFromDTO(setting, dto);
        setting.setUpdatedAt(LocalDateTime.now());

        return SystemSettingMapper.toResponseDTO(settingRepository.save(setting));
    }
//    @Override
//    public SystemSetting update(Long id, SystemSettingPatchDTO dto) {
//        SystemSetting setting = settingRepository.findById(id).orElseThrow();
//
//        if (dto.getCommissionRate() != null) setting.setCommissionRate(dto.getCommissionRate());
//        if (dto.getMaxVoucherPerType() != null) setting.setMaxVoucherPerType(dto.getMaxVoucherPerType());
//        if (dto.getCancelFee() != null) setting.setCancelFee(dto.getCancelFee());
//        if (dto.getCancelTimeLimit() != null) setting.setCancelTimeLimit(dto.getCancelTimeLimit());
//        if (dto.getPriceSeatRegular() != null) setting.setPriceSeatRegular(dto.getPriceSeatRegular());
//        if (dto.getPriceSeatVip() != null) setting.setPriceSeatVip(dto.getPriceSeatVip());
//        if (dto.getPriceSeatDouble() != null) setting.setPriceSeatDouble(dto.getPriceSeatDouble());
//
//        setting.setUpdatedAt(LocalDateTime.now());
//
//        return settingRepository.save(setting);
//    }

    @Override
    public void delete(Long id) {
        settingRepository.deleteById(id);
    }

    // Optional: lấy toàn bộ setting (admin tool)
    public List<SystemSetting> getAllSettings() {
        return settingRepository.findAll();
    }
}
