package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.BookingDTO;
import com.example.movieticketbookingbe.dto.BookingRequestDTO;
import com.example.movieticketbookingbe.model.Booking;
import com.example.movieticketbookingbe.model.Booking.BookingSeatInfo;
import com.example.movieticketbookingbe.model.Booking.BookingFoodInfo;
import java.util.stream.Collectors;

public class BookingMapper {
    public static BookingDTO toDTO(Booking booking) {
        if (booking == null) return null;
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setUserId(booking.getUserId());
        dto.setShowtimeId(booking.getShowtimeId());
        dto.setBookingTime(booking.getBookingTime());
        dto.setTotalAmount(booking.getTotalAmount());
        dto.setStatus(booking.getStatus() != null ? booking.getStatus().name() : null);
        dto.setIsActive(booking.getIsActive());
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setUpdatedAt(booking.getUpdatedAt());
        if (booking.getBookingSeats() != null) {
            dto.setBookingSeats(booking.getBookingSeats().stream().map(seat -> {
                BookingDTO.BookingSeatInfoDTO seatDTO = new BookingDTO.BookingSeatInfoDTO();
                seatDTO.setSeatId(seat.getSeatId());
                seatDTO.setPrice(seat.getPrice());
                seatDTO.setSeatName(seat.getSeatName());
                seatDTO.setSeatType(seat.getSeatType());
                seatDTO.setRowNumber(seat.getRowNumber());
                seatDTO.setColumnNumber(seat.getColumnNumber());
                return seatDTO;
            }).collect(Collectors.toList()));
        }
        if (booking.getBookingFoods() != null) {
            dto.setBookingFoods(booking.getBookingFoods().stream().map(food -> {
                BookingDTO.BookingFoodInfoDTO foodDTO = new BookingDTO.BookingFoodInfoDTO();
                foodDTO.setFoodInventoryId(food.getFoodInventoryId());
                foodDTO.setQuantity(food.getQuantity());
                foodDTO.setPrice(food.getPrice());
                foodDTO.setFoodName(food.getFoodName());
                return foodDTO;
            }).collect(Collectors.toList()));
        }
        return dto;
    }

    public static Booking toEntity(BookingRequestDTO dto) {
        if (dto == null) return null;
        Booking booking = new Booking();
        booking.setUserId(dto.getUserId());
        booking.setShowtimeId(dto.getShowtimeId());
        booking.setBookingTime(dto.getBookingTime());
        booking.setTotalAmount(dto.getTotalAmount());
        booking.setStatus(dto.getStatus());
        if (dto.getBookingSeats() != null) {
            booking.setBookingSeats(dto.getBookingSeats().stream().map(seatDTO -> {
                BookingSeatInfo seatInfo = new BookingSeatInfo();
                seatInfo.setSeatId(seatDTO.getSeatId());
                seatInfo.setPrice(seatDTO.getPrice());
                return seatInfo;
            }).collect(Collectors.toList()));
        }
        if (dto.getBookingFoods() != null) {
            booking.setBookingFoods(dto.getBookingFoods().stream().map(foodDTO -> {
                BookingFoodInfo foodInfo = new BookingFoodInfo();
                foodInfo.setFoodInventoryId(foodDTO.getFoodInventoryId());
                foodInfo.setQuantity(foodDTO.getQuantity());
                foodInfo.setPrice(foodDTO.getPrice());
                return foodInfo;
            }).collect(Collectors.toList()));
        }
        return booking;
    }
} 