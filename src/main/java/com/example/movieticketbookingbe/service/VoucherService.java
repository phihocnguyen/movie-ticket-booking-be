package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.model.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherService {
    Voucher createVoucher(Voucher voucher);

    Voucher updateVoucher(Long id, Voucher voucher);

    void deleteVoucher(Long id);

    Optional<Voucher> getVoucherById(Long id);

    List<Voucher> getAllVouchers();

    List<Voucher> getActiveVouchers();

    Optional<Voucher> getVoucherByCode(String code);

    boolean existsByCode(String code);

    List<Voucher> searchVouchers(String code);

    List<Voucher> getValidVouchers();
}