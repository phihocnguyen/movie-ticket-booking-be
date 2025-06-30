package com.example.movieticketbookingbe.dto.booking;

import com.example.movieticketbookingbe.dto.user.UserDTO;
import com.example.movieticketbookingbe.dto.showtime.ShowtimeDTO;
import com.example.movieticketbookingbe.dto.seat.SeatDTO;
import com.example.movieticketbookingbe.dto.payment.PaymentDTO;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingDTO {
    private Long id;
    private UserDTO user;
    private Long userId;
    private ShowtimeDTO showtime;
    private SeatDTO seat;
    private PaymentDTO payment;
    private String status;
    private LocalDateTime bookingTime;
    private Double totalTicketPrice;
    private Double totalFoodPrice;
    private Double totalAmount;
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