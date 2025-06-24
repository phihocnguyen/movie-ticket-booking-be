package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.UserVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
 
public interface UserVoucherRepository extends JpaRepository<UserVoucher, Long> {
    List<UserVoucher> findByUserId(Long userId);
    boolean existsByUserIdAndVoucherId(Long userId, Long voucherId);
} 