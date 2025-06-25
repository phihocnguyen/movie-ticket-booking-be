package com.example.movieticketbookingbe.dto.systemsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemSettingCreateDTO {
    private Long ownerId;

    private BigDecimal commissionRate;
    private Integer maxVoucherPerType;

    private BigDecimal cancelFee;
    private Integer cancelTimeLimit;

    private Integer priceSeatRegular;
    private Integer priceSeatVip;
    private Integer priceSeatDouble;
}
