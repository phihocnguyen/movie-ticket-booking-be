package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.model.Voucher;
import java.util.List;
import com.example.movieticketbookingbe.dto.voucher.VoucherPatchDTO;

public interface VoucherService {
    Voucher createVoucher(Voucher voucher);
    List<Voucher> getAllVouchers();
    Voucher getVoucherById(Long id);
    Voucher updateVoucher(Long id, VoucherPatchDTO patchDTO);
    void deleteVoucher(Long id);
} 