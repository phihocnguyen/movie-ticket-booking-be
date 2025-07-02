package com.example.movieticketbookingbe.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "system_setting")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private TheaterOwner owner; // nullable => setting toàn hệ thống

    @Column(name = "commission_rate", nullable = true)
    private BigDecimal commissionRate;

    @Column(name = "max_voucher_per_type", nullable = true)
    private Integer maxVoucherPerType;

//    @Column(name = "cancel_fee")
//    private BigDecimal cancelFee;
//
//    @Column(name = "cancel_time_limit")
//    private Integer cancelTimeLimit;

    @Column(name = "price_seat_regular")
    private Double  priceSeatRegular;

    @Column(name = "price_seat_vip")
    private Double  priceSeatVip;

    @Column(name = "price_seat_double")
    private Double  priceSeatDouble;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
