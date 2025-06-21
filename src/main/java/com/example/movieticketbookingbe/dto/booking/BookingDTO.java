package com.example.movieticketbookingbe.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingDTO {
    private Long id;
    private Long userId;
    private Long showtimeId;
    private LocalDateTime bookingTime;
    private Double totalAmount;
    private String status;
    private Boolean isActive;
    private List<BookingSeatInfoDTO> bookingSeats;
    private List<BookingFoodInfoDTO> bookingFoods;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    public static class BookingSeatInfoDTO {
        private Long seatId;
        private Double price;
        private String seatName;
        private String seatType;
        private Integer rowNumber;
        private Integer columnNumber;
    }

    @Data
    public static class BookingFoodInfoDTO {
        private Long foodInventoryId;
        private Integer quantity;
        private Double price;
        private String foodName;
    }
} 