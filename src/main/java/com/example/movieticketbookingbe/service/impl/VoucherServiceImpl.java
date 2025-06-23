package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.Voucher;
import com.example.movieticketbookingbe.repository.VoucherRepository;
import com.example.movieticketbookingbe.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.movieticketbookingbe.dto.voucher.VoucherPatchDTO;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;

    @Override
    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher getVoucherById(Long id) {
        return voucherRepository.findById(id).orElse(null);
    }

    @Override
    public Voucher updateVoucher(Long id, VoucherPatchDTO patchDTO) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow();
        if (patchDTO.getDescription() != null) voucher.setDescription(patchDTO.getDescription());
        if (patchDTO.getDiscountAmount() != null) voucher.setDiscountAmount(patchDTO.getDiscountAmount());
        if (patchDTO.getMinPrice() != null) voucher.setMinPrice(patchDTO.getMinPrice());
        if (patchDTO.getType() != null) voucher.setType(patchDTO.getType());
        if (patchDTO.getStartDate() != null) voucher.setStartDate(patchDTO.getStartDate());
        if (patchDTO.getEndDate() != null) voucher.setEndDate(patchDTO.getEndDate());
        if (patchDTO.getMaxUses() != null) voucher.setMaxUses(patchDTO.getMaxUses());
        if (patchDTO.getIsActive() != null) voucher.setIsActive(patchDTO.getIsActive());
        return voucherRepository.save(voucher);
    }

    @Override
    public void deleteVoucher(Long id) {
        voucherRepository.deleteById(id);
    }
} 