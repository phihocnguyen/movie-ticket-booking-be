package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    boolean existsByCode(String code);
} 