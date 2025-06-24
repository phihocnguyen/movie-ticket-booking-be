package com.example.movieticketbookingbe.dto.voucher;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VoucherDTO {
    private Long id;
    private String code;
    private String description;
    private BigDecimal discountAmount;
    private BigDecimal minPrice;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer maxUses;
    private Integer usedCount;
    private Boolean isActive;

}
