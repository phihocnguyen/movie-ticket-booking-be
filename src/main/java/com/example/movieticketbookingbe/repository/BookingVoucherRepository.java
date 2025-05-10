package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.BookingVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingVoucherRepository extends JpaRepository<BookingVoucher, Long> {
    List<BookingVoucher> findByBookingId(Long bookingId);

    List<BookingVoucher> findByVoucherId(Long voucherId);

    boolean existsByBookingIdAndVoucherId(Long bookingId, Long voucherId);
}