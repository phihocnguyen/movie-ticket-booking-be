package com.example.movieticketbookingbe.dto.systemsetting;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemSettingDTO {
    private Long id;
    private Long ownerId;

    private BigDecimal commissionRate;
    private Integer maxVoucherPerType;

    private BigDecimal cancelFee;
    private Integer cancelTimeLimit;

    private Double  priceSeatRegular;
    private Double  priceSeatVip;
    private Double  priceSeatDouble;
}