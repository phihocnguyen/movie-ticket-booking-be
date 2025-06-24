package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import com.example.movieticketbookingbe.dto.movie.MovieDTO;
import com.example.movieticketbookingbe.dto.voucher.VoucherCreateDTO;
import com.example.movieticketbookingbe.dto.voucher.VoucherDTO;
import com.example.movieticketbookingbe.mapper.MovieMapper;
import com.example.movieticketbookingbe.mapper.VoucherMapper;
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
    public ResponseEntity<ApiResponseDTO<VoucherDTO>> createVoucher(@RequestBody VoucherCreateDTO voucherCreateDTO) {
        Voucher voucher = VoucherMapper.toEntity(voucherCreateDTO);
        VoucherDTO dto = VoucherMapper.toResponseDTO(voucherService.createVoucher(voucher));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Created", dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<VoucherDTO>>> getAllVouchers() {
        List<VoucherDTO> dtos = voucherService.getAllVouchers().stream().map(VoucherMapper::toResponseDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy danh sách voucher thành công",dtos ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Voucher>> getVoucherById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "OK", voucherService.getVoucherById(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<VoucherDTO>> updateVoucher(@PathVariable Long id, @RequestBody VoucherPatchDTO patchDTO) {
        VoucherDTO dto = VoucherMapper.toResponseDTO(voucherService.updateVoucher(id, patchDTO));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Updated voucher successfully", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteVoucher(@PathVariable Long id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Deleted", null));
    }
} 