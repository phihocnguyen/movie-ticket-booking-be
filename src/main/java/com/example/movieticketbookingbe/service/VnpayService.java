package com.example.movieticketbookingbe.service;

public interface VnpayService {
    String createVnpayPaymentUrl(Long bookingId, double amount, String returnUrl, String clientIp);
    boolean validateVnpayResponse(java.util.Map<String, String> vnpayParams);
    String handleVnpayReturn(java.util.Map<String, String> vnpayParams);
} 