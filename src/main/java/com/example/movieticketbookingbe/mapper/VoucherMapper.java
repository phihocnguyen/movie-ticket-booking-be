package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.voucher.VoucherCreateDTO;
import com.example.movieticketbookingbe.dto.voucher.VoucherDTO;
import com.example.movieticketbookingbe.dto.voucher.VoucherPatchDTO;
import com.example.movieticketbookingbe.model.Voucher;

import java.time.LocalDateTime;

public class VoucherMapper {

    // Convert Entity -> Response DTO
    public static VoucherDTO toResponseDTO(Voucher voucher) {
        VoucherDTO dto = new VoucherDTO();
        dto.setId(voucher.getId());
        dto.setCode(voucher.getCode());
        dto.setDescription(voucher.getDescription());
        dto.setDiscountAmount(voucher.getDiscountAmount());
        dto.setMinPrice(voucher.getMinPrice());
        dto.setType(voucher.getType());
        dto.setStartDate(voucher.getStartDate());
        dto.setEndDate(voucher.getEndDate());
        dto.setMaxUses(voucher.getMaxUses());
        dto.setUsedCount(voucher.getUsedCount());
        dto.setIsActive(voucher.getIsActive());
        return dto;
    }

    // Convert CreateDTO -> Entity (Tạo mới)
    public static Voucher toEntity(VoucherCreateDTO dto) {
        Voucher voucher = new Voucher();
        voucher.setCode(dto.getCode());
        voucher.setDescription(dto.getDescription());
        voucher.setDiscountAmount(dto.getDiscountAmount());
        voucher.setMinPrice(dto.getMinPrice());
        voucher.setType(dto.getType());
        voucher.setStartDate(dto.getStartDate());
        voucher.setEndDate(dto.getEndDate());
        voucher.setMaxUses(dto.getMaxUses());
        voucher.setUsedCount(0); // default
        voucher.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        voucher.setCreatedAt(LocalDateTime.now());
        voucher.setUpdatedAt(LocalDateTime.now());
        return voucher;
    }

    // Cập nhật Entity từ PatchDTO (Update)
    public static void updateEntityFromDTO(Voucher voucher, VoucherPatchDTO dto) {
        voucher.setDescription(dto.getDescription());
        voucher.setDiscountAmount(dto.getDiscountAmount());
        voucher.setMinPrice(dto.getMinPrice());
        voucher.setType(dto.getType());
        voucher.setStartDate(dto.getStartDate());
        voucher.setEndDate(dto.getEndDate());
        voucher.setMaxUses(dto.getMaxUses());
        if (dto.getIsActive() != null) {
            voucher.setIsActive(dto.getIsActive());
        }
        voucher.setUpdatedAt(LocalDateTime.now());
    }
}
