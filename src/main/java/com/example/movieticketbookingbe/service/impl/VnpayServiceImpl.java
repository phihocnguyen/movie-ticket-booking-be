package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.service.VnpayService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class VnpayServiceImpl implements VnpayService {
    @Value("${vnpay.tmnCode}")
    private String vnpTmnCode;
    @Value("${vnpay.hashSecret}")
    private String vnpHashSecret;
    @Value("${vnpay.payUrl}")
    private String vnpPayUrl;
    @Value("${vnpay.returnUrl}")
    private String vnpReturnUrl;

    @Override
public String createVnpayPaymentUrl(Long bookingId, double amount, String returnUrl, String clientIp) {
    try {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = "Thanh toan don hang " + bookingId;
        String vnp_OrderType = "other";
        String vnp_TxnRef = String.valueOf(bookingId);
        String vnp_IpAddr = clientIp;
        String vnp_CurrCode = "VND";
        long vnp_Amount = (long) (amount * 100);
        String vnp_Locale = "vn";

        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        String vnp_CreateDate = ZonedDateTime.now(zoneId).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnpTmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(vnp_Amount));
        vnp_Params.put("vnp_CurrCode", vnp_CurrCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", vnp_OrderType);
        vnp_Params.put("vnp_Locale", vnp_Locale);
        vnp_Params.put("vnp_ReturnUrl", returnUrl != null ? returnUrl : vnpReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        
        for (String fieldName : fieldNames) {
            String value = vnp_Params.get(fieldName);
            if (value != null && !value.isEmpty()) {
                //Build hash data
                if (hashData.length() > 0) {
                    hashData.append('&');
                }
                hashData.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()));
                
                //Build query
                 if (query.length() > 0) {
                    query.append('&');
                }
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()))
                     .append('=')
                     .append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()));
            }
        }
        
        String secureHash = hmacSHA512(vnpHashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(secureHash);
        
        return vnpPayUrl + "?" + query.toString();
    } catch (Exception e) {
        throw new RuntimeException("Error creating VNPAY payment URL", e);
    }
}


private String hmacSHA512(String key, String data) throws Exception {
    Mac hmac512 = Mac.getInstance("HmacSHA512");
    SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA512");
    hmac512.init(secretKey);
    byte[] bytes = hmac512.doFinal(data.getBytes("UTF-8"));
    StringBuilder hash = new StringBuilder();
    for (byte b : bytes) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hash.append('0');
        hash.append(hex);
    }
    return hash.toString();
}

@Override
public boolean validateVnpayResponse(Map<String, String> vnpayParams) {
    try {
        String vnp_SecureHash = vnpayParams.get("vnp_SecureHash");
        if (vnp_SecureHash == null) return false;

        // Loại bỏ vnp_SecureHash và vnp_SecureHashType khỏi params để tính hash
        vnpayParams.remove("vnp_SecureHash");
        vnpayParams.remove("vnp_SecureHashType");

        List<String> fieldNames = new ArrayList<>(vnpayParams.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        
        for (String fieldName : fieldNames) {
            String value = vnpayParams.get(fieldName);
            if (value != null && !value.isEmpty()) {
                if (hashData.length() > 0) {
                    hashData.append('&');
                }
                hashData.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()));
            }
        }
        
        String myHash = hmacSHA512(vnpHashSecret, hashData.toString());
        return vnp_SecureHash.equalsIgnoreCase(myHash);
    } catch (Exception e) {
        return false;
    }
}

    @Override
    public String handleVnpayReturn(Map<String, String> vnpayParams) {
        return null;
    }
} 