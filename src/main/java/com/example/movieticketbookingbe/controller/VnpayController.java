package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.service.VnpayService;
import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

@RestController
@RequestMapping("/api/vnpay")
@RequiredArgsConstructor
@Tag(name = "VNPAY", description = "VNPAY payment APIs")
public class VnpayController {
    private final VnpayService vnpayService;

    @PostMapping("/create-url")
    public ResponseEntity<ApiResponseDTO<String>> createVnpayPaymentUrl(@RequestParam Long bookingId,
                                                                       @RequestParam double amount,
                                                                       @RequestParam String returnUrl,
                                                                       HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null) clientIp = request.getRemoteAddr();
        String url = vnpayService.createVnpayPaymentUrl(bookingId, amount, returnUrl, clientIp);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Tạo URL thanh toán VNPAY thành công", url));
    }

    @PostMapping("/validate")
    public ResponseEntity<ApiResponseDTO<Boolean>> validateVnpayResponse(@RequestBody Map<String, String> vnpayParams) {
        boolean valid = vnpayService.validateVnpayResponse(vnpayParams);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Validate VNPAY response", valid));
    }

    @GetMapping("/return")
    public ResponseEntity<ApiResponseDTO<String>> handleVnpayReturn(@RequestParam Map<String, String> vnpayParams) {
        String result = vnpayService.handleVnpayReturn(vnpayParams);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Xử lý kết quả thanh toán VNPAY", result));
    }
} 