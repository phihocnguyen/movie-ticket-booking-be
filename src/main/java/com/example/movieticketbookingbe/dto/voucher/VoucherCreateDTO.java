package com.example.movieticketbookingbe.dto.voucher;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VoucherCreateDTO {
    private String code;
    private String description;
    private BigDecimal discountAmount;
    private BigDecimal minPrice;
    private String type; // 'new_user', 'seasonal'
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate  startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private Integer maxUses;
    private Boolean isActive;
} 