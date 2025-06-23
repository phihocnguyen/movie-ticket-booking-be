package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import com.example.movieticketbookingbe.model.Voucher;
import com.example.movieticketbookingbe.service.VoucherService;
import com.example.movieticketbookingbe.dto.voucher.VoucherPatchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/voucher")
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<Voucher>> createVoucher(@RequestBody Voucher voucher) {
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Created", voucherService.createVoucher(voucher)));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<Voucher>>> getAllVouchers() {
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "OK", voucherService.getAllVouchers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Voucher>> getVoucherById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "OK", voucherService.getVoucherById(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Voucher>> updateVoucher(@PathVariable Long id, @RequestBody VoucherPatchDTO patchDTO) {
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Updated", voucherService.updateVoucher(id, patchDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteVoucher(@PathVariable Long id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Deleted", null));
    }
} 