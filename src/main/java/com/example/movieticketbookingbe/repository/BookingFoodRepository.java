package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.BookingFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingFoodRepository extends JpaRepository<BookingFood, Long> {
    List<BookingFood> findByBookingId(Long bookingId);

    List<BookingFood> findByFoodInventoryId(Long foodInventoryId);

    boolean existsByBookingIdAndFoodInventoryId(Long bookingId, Long foodInventoryId);
}