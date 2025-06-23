package com.example.movieticketbookingbe.dto.voucher;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class VoucherPatchDTO {
    private String description;
    private BigDecimal discountAmount;
    private BigDecimal minPrice;
    private String type; // 'new_user', 'seasonal'
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer maxUses;
    private Boolean isActive;
} 