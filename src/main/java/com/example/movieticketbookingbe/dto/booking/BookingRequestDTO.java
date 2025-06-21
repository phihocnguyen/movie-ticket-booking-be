package com.example.movieticketbookingbe.dto;

import com.example.movieticketbookingbe.model.Booking.BookingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingRequestDTO {
    private Long userId;
    private Long showtimeId;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime bookingTime;

    private Double totalAmount;
    private BookingStatus status;

    private List<BookingSeatDTO> bookingSeats;
    private List<BookingFoodDTO> bookingFoods;

    @Data
    public static class BookingSeatDTO {
        private Long seatId;
        private Double price;
    }

    @Data
    public static class BookingFoodDTO {
        private Long foodInventoryId;
        private Integer quantity;
        private Double price;
    }
}