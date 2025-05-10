package com.example.movieticketbookingbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vouchers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double discountAmount;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private Integer maxUses;

    @Column(nullable = false)
    private Integer usedCount = 0;

    @Column(nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "voucher")
    private List<BookingVoucher> bookingVouchers;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}