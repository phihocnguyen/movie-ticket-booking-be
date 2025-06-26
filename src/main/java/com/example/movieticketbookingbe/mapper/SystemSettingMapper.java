package com.example.movieticketbookingbe.mapper;
import com.example.movieticketbookingbe.dto.systemsetting.SystemSettingCreateDTO;
import com.example.movieticketbookingbe.dto.systemsetting.SystemSettingDTO;
import com.example.movieticketbookingbe.dto.systemsetting.SystemSettingPatchDTO;
import com.example.movieticketbookingbe.model.SystemSetting;
import com.example.movieticketbookingbe.model.TheaterOwner;

import java.time.LocalDateTime;

public class SystemSettingMapper {

    // Entity -> ResponseDTO
    public static SystemSettingDTO toResponseDTO(SystemSetting setting) {
        if (setting == null) {
            return null;
        }
        SystemSettingDTO dto = new SystemSettingDTO();
        dto.setId(setting.getId());
        dto.setOwnerId(setting.getOwner() != null ? setting.getOwner().getId() : null);
        dto.setCommissionRate(setting.getCommissionRate());
        dto.setMaxVoucherPerType(setting.getMaxVoucherPerType());
        dto.setCancelFee(setting.getCancelFee());
        dto.setCancelTimeLimit(setting.getCancelTimeLimit());
        dto.setPriceSeatRegular(setting.getPriceSeatRegular());
        dto.setPriceSeatVip(setting.getPriceSeatVip());
        dto.setPriceSeatDouble(setting.getPriceSeatDouble());
        return dto;
    }

    // CreateDTO -> Entity
    public static SystemSetting toEntity(SystemSettingCreateDTO dto, TheaterOwner owner) {
        SystemSetting setting = new SystemSetting();
        setting.setOwner(owner);
        setting.setCommissionRate(dto.getCommissionRate());
        setting.setMaxVoucherPerType(dto.getMaxVoucherPerType());
        setting.setCancelFee(dto.getCancelFee());
        setting.setCancelTimeLimit(dto.getCancelTimeLimit());
        setting.setPriceSeatRegular(dto.getPriceSeatRegular());
        setting.setPriceSeatVip(dto.getPriceSeatVip());
        setting.setPriceSeatDouble(dto.getPriceSeatDouble());
        setting.setCreatedAt(LocalDateTime.now());
        setting.setUpdatedAt(LocalDateTime.now());
        return setting;
    }

    // PatchDTO -> Update Entity
    public static void updateEntityFromDTO(SystemSetting setting, SystemSettingPatchDTO dto) {
        if (dto.getCommissionRate() != null) {
            setting.setCommissionRate(dto.getCommissionRate());
        }
        if (dto.getMaxVoucherPerType() != null) {
            setting.setMaxVoucherPerType(dto.getMaxVoucherPerType());
        }
        if (dto.getCancelFee() != null) {
            setting.setCancelFee(dto.getCancelFee());
        }
        if (dto.getCancelTimeLimit() != null) {
            setting.setCancelTimeLimit(dto.getCancelTimeLimit());
        }
        if (dto.getPriceSeatRegular() != null) {
            setting.setPriceSeatRegular(dto.getPriceSeatRegular());
        }
        if (dto.getPriceSeatVip() != null) {
            setting.setPriceSeatVip(dto.getPriceSeatVip());
        }
        if (dto.getPriceSeatDouble() != null) {
            setting.setPriceSeatDouble(dto.getPriceSeatDouble());
        }
        setting.setUpdatedAt(LocalDateTime.now());
    }
}
