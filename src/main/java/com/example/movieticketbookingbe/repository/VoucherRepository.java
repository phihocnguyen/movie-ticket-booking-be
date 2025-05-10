package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    List<Voucher> findByIsActiveTrue();

    Optional<Voucher> findByCode(String code);

    boolean existsByCode(String code);

    List<Voucher> findByCodeContainingIgnoreCase(String code);

    List<Voucher> findByEndDateAfter(LocalDateTime date);
}