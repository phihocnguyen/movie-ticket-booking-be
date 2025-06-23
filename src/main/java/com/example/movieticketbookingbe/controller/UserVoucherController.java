package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import com.example.movieticketbookingbe.model.UserVoucher;
import com.example.movieticketbookingbe.service.UserVoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user-voucher")
@RequiredArgsConstructor
public class UserVoucherController {
    private final UserVoucherService userVoucherService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<UserVoucher>> assignVoucherToUser(@RequestParam Long userId, @RequestParam Long voucherId) {
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Assigned", userVoucherService.assignVoucherToUser(userId, voucherId)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDTO<List<UserVoucher>>> getVouchersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "OK", userVoucherService.getVouchersByUser(userId)));
    }

    @PatchMapping("/{id}/use")
    public ResponseEntity<ApiResponseDTO<UserVoucher>> markVoucherAsUsed(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Updated", userVoucherService.markVoucherAsUsed(id)));
    }
} 