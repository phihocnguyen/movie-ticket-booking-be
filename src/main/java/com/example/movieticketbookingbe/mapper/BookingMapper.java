package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.booking.BookingDTO;
import com.example.movieticketbookingbe.dto.booking.BookingRequestDTO;
import com.example.movieticketbookingbe.model.Booking;
import com.example.movieticketbookingbe.model.Booking.BookingSeatInfo;
import com.example.movieticketbookingbe.model.Booking.BookingFoodInfo;

import java.util.List;
import java.util.stream.Collectors;
import com.example.movieticketbookingbe.dto.user.UserDTO;
import com.example.movieticketbookingbe.dto.showtime.ShowtimeDTO;
import com.example.movieticketbookingbe.dto.seat.SeatDTO;
import com.example.movieticketbookingbe.dto.payment.PaymentDTO;
import com.example.movieticketbookingbe.model.User;
import com.example.movieticketbookingbe.repository.UserRepository;

public class BookingMapper {
    public static BookingDTO toDTO(Booking booking) {
        if (booking == null) return null;

        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());

        // Map user
        if (booking.getUser() != null) {
            UserDTO userDTO = UserMapper.toDTO(booking.getUser());
            dto.setUser(userDTO);
            dto.setUserId(booking.getUser().getId());
        }

        // Map showtime
        dto.setShowtime(booking.getShowtime() != null ? ShowtimeMapper.toDTO(booking.getShowtime()) : null);

        // Map payment
        if (booking.getPayment() != null) {
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setId(booking.getPayment().getId());
            paymentDTO.setAmount(booking.getPayment().getAmount());
            paymentDTO.setStatus(booking.getPayment().getStatus());
            paymentDTO.setCreatedAt(booking.getPayment().getCreatedAt());
            paymentDTO.setUpdatedAt(booking.getPayment().getUpdatedAt());
            dto.setPayment(paymentDTO);
        } else {
            dto.setPayment(null);
        }

        dto.setStatus(booking.getStatus() != null ? booking.getStatus().name() : null);
        dto.setBookingTime(booking.getBookingTime());
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setUpdatedAt(booking.getUpdatedAt());

        // Map booking seats
        double totalTicketPrice = 0.0;
        if (booking.getBookingSeats() != null) {
            List<BookingDTO.BookingSeatInfoDTO> seatDTOs = booking.getBookingSeats().stream().map(seat -> {
                BookingDTO.BookingSeatInfoDTO seatDTO = new BookingDTO.BookingSeatInfoDTO();
                seatDTO.setSeatId(seat.getSeatId());
                seatDTO.setPrice(seat.getPrice());
                seatDTO.setSeatName(seat.getSeatName());
                seatDTO.setSeatType(seat.getSeatType());
                seatDTO.setRowNumber(seat.getRowNumber());
                seatDTO.setColumnNumber(seat.getColumnNumber());
                return seatDTO;
            }).collect(Collectors.toList());

            totalTicketPrice = booking.getBookingSeats().stream()
                    .mapToDouble(seat -> seat.getPrice() != null ? seat.getPrice() : 0.0)
                    .sum();

            dto.setBookingSeats(seatDTOs);
        }

        // Map booking foods
        double totalFoodPrice = 0.0;
        if (booking.getBookingFoods() != null) {
            List<BookingDTO.BookingFoodInfoDTO> foodDTOs = booking.getBookingFoods().stream().map(food -> {
                BookingDTO.BookingFoodInfoDTO foodDTO = new BookingDTO.BookingFoodInfoDTO();
                foodDTO.setFoodInventoryId(food.getFoodInventoryId());
                foodDTO.setQuantity(food.getQuantity());
                foodDTO.setPrice(food.getPrice());
                foodDTO.setFoodName(food.getFoodName());
                return foodDTO;
            }).collect(Collectors.toList());

            totalFoodPrice = booking.getBookingFoods().stream()
                    .mapToDouble(food -> {
                        double price = food.getPrice() != null ? food.getPrice() : 0.0;
                        int quantity = food.getQuantity() != null ? food.getQuantity() : 0;
                        return price * quantity;
                    })
                    .sum();

            dto.setBookingFoods(foodDTOs);
        }

        // Gán tổng tiền
        dto.setTotalTicketPrice(totalTicketPrice);
        dto.setTotalFoodPrice(totalFoodPrice);
        dto.setTotalAmount(totalTicketPrice + totalFoodPrice);

        return dto;
    }


    public static Booking toEntity(BookingRequestDTO dto, User user) {
        if (dto == null) return null;
        Booking booking = new Booking();
        booking.setUser(user);
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