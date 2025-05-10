package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.Voucher;
import com.example.movieticketbookingbe.repository.VoucherRepository;
import com.example.movieticketbookingbe.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;

    @Override
    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher updateVoucher(Long id, Voucher voucher) {
        Voucher existingVoucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found"));
        voucher.setId(existingVoucher.getId());
        return voucherRepository.save(voucher);
    }

    @Override
    public void deleteVoucher(Long id) {
        voucherRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Voucher> getVoucherById(Long id) {
        return voucherRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Voucher> getActiveVouchers() {
        return voucherRepository.findByIsActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Voucher> getVoucherByCode(String code) {
        return voucherRepository.findByCode(code);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCode(String code) {
        return voucherRepository.existsByCode(code);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Voucher> searchVouchers(String code) {
        return voucherRepository.findByCodeContainingIgnoreCase(code);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Voucher> getValidVouchers() {
        return voucherRepository.findByEndDateAfter(LocalDateTime.now());
    }
}