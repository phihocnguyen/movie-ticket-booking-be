package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.dto.booking.BookingRequestDTO;
import com.example.movieticketbookingbe.dto.booking.BookingPatchDTO;
import com.example.movieticketbookingbe.model.Booking;
import com.example.movieticketbookingbe.model.Seat;
import com.example.movieticketbookingbe.model.TheaterFoodInventory;
import com.example.movieticketbookingbe.model.Booking.BookingStatus;
import com.example.movieticketbookingbe.model.User;
import com.example.movieticketbookingbe.repository.BookingRepository;
import com.example.movieticketbookingbe.repository.SeatRepository;
import com.example.movieticketbookingbe.repository.TheaterFoodInventoryRepository;
import com.example.movieticketbookingbe.repository.UserRepository;
import com.example.movieticketbookingbe.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final TheaterFoodInventoryRepository foodInventoryRepository;
    private final UserRepository userRepository;

    @Override
    public Booking createBooking(Booking booking) {
        // Check if any seat is inactive
        if (booking.getBookingSeats() != null) {
            for (Booking.BookingSeatInfo seatInfo : booking.getBookingSeats()) {
                Seat seat = seatRepository.findById(seatInfo.getSeatId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy ghế"));

                if (!seat.getIsActive()) {
                    System.out.println("Ghế " + seat.getSeatNumber() + " đã được đặt");
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Ghế " + seat.getSeatNumber() + " đã được đặt");
                }
            }
        }

        // Set initial status if not provided
        if (booking.getStatus() == null) {
            booking.setStatus(BookingStatus.PENDING);
        }

        // Set isActive to true by default
        booking.setIsActive(true);

        // Save the booking
        Booking savedBooking = bookingRepository.save(booking);

        // Update seat status to inactive
        if (booking.getBookingSeats() != null) {
            booking.getBookingSeats().forEach(seatInfo -> {
                seatRepository.findById(seatInfo.getSeatId()).ifPresent(seat -> {
                    seat.setIsActive(false);
                    seatRepository.save(seat);
                });
            });
        }

        loadBookingInfo(savedBooking);
        return savedBooking;
    }

    @Override
    public Booking updateBooking(Long id, Booking booking) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setId(existingBooking.getId());
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        loadBookingInfo(booking);
        return Optional.of(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        bookings.forEach(this::loadBookingInfo);
        return bookings;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getActiveBookings() {
        List<Booking> bookings = bookingRepository.findByIsActiveTrue();
        bookings.forEach(this::loadBookingInfo);
        return bookings;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByUser(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        bookings.forEach(this::loadBookingInfo);
        return bookings;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByShowtime(Long showtimeId) {
        List<Booking> bookings = bookingRepository.findByShowtimeId(showtimeId);
        bookings.forEach(this::loadBookingInfo);
        return bookings;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> searchBookings(Long userId, Long showtimeId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Booking> bookings = bookingRepository.findByUserIdAndShowtimeIdAndBookingTimeBetween(userId, showtimeId,
                startTime, endTime);
        bookings.forEach(this::loadBookingInfo);
        return bookings;
    }

    @Override
    public Booking cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(BookingStatus.CANCELLED);
        booking.setIsActive(false);

        // Reactivate seats when booking is cancelled
        if (booking.getBookingSeats() != null) {
            booking.getBookingSeats().forEach(seatInfo -> {
                seatRepository.findById(seatInfo.getSeatId()).ifPresent(seat -> {
                    seat.setIsActive(true);
                    seatRepository.save(seat);
                });
            });
        }

        return bookingRepository.save(booking);
    }

    @Override
    public Booking completeBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(BookingStatus.COMPLETED);
        return bookingRepository.save(booking);
    }
    public List<Booking> getBookingsByOwner(Long ownerId) {
        return bookingRepository.findAllByOwnerId(ownerId);
    }
    @Override
    public Booking createBookingFromDTO(BookingRequestDTO dto) {
        Booking booking = new Booking();

        // Lấy user từ DB
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
            booking.setUser(user);
        }

        booking.setShowtimeId(dto.getShowtimeId());
        booking.setBookingTime(dto.getBookingTime());
        booking.setTotalAmount(dto.getTotalAmount());
        booking.setStatus(dto.getStatus());

        if (dto.getBookingSeats() != null) {
            booking.setBookingSeats(dto.getBookingSeats().stream().map(seatDTO -> {
                Booking.BookingSeatInfo seatInfo = new Booking.BookingSeatInfo();
                seatInfo.setSeatId(seatDTO.getSeatId());
                seatInfo.setPrice(seatDTO.getPrice());
                return seatInfo;
            }).collect(Collectors.toList()));
        }

        if (dto.getBookingFoods() != null) {
            booking.setBookingFoods(dto.getBookingFoods().stream().map(foodDTO -> {
                Booking.BookingFoodInfo foodInfo = new Booking.BookingFoodInfo();
                foodInfo.setFoodInventoryId(foodDTO.getFoodInventoryId());
                foodInfo.setQuantity(foodDTO.getQuantity());
                foodInfo.setPrice(foodDTO.getPrice());
                return foodInfo;
            }).collect(Collectors.toList()));
        }

        return createBooking(booking); // gọi lại logic cũ
    }
    @Override
    public Booking patchBooking(Long id, BookingPatchDTO patchDTO) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
//        if (patchDTO.getShowtimeId() != null) {
//            // TODO: set showtime entity nếu cần
//        }
//        if (patchDTO.getUserId() != null) {
//            // TODO: set user entity nếu cần
//        }
//        if (patchDTO.getTotalPrice() != null) booking.setTotalAmount(Double.valueOf(patchDTO.getTotalPrice()));
//        if (patchDTO.getPaymentMethod() != null) booking.setPaymentMethod(patchDTO.getPaymentMethod());
//        if (patchDTO.getNote() != null) booking.setNote(patchDTO.getNote());
//        if (patchDTO.getIsActive() != null) booking.setIsActive(patchDTO.getIsActive());
//        if (patchDTO.getBookingTime() != null) booking.setBookingTime(patchDTO.getBookingTime());
        if (patchDTO.getStatus() != null) {
            try {
                Booking.BookingStatus statusEnum = Booking.BookingStatus.valueOf(patchDTO.getStatus());
                booking.setStatus(statusEnum);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid booking status: " + patchDTO.getStatus());
            }
        }
        return bookingRepository.save(booking);
    }

    private void loadSeatInfo(Booking booking) {
        if (booking.getBookingSeats() != null) {
            for (Booking.BookingSeatInfo seatInfo : booking.getBookingSeats()) {
                Seat seat = seatRepository.findById(seatInfo.getSeatId()).orElse(null);
                if (seat != null) {
                    seatInfo.setSeatName(seat.getSeatNumber());
                    seatInfo.setSeatType(seat.getSeatTypeName());
                }
            }
        }
    }

    private void loadFoodInfo(Booking booking) {
        if (booking.getBookingFoods() != null) {
            for (Booking.BookingFoodInfo foodInfo : booking.getBookingFoods()) {
                TheaterFoodInventory food = foodInventoryRepository.findById(foodInfo.getFoodInventoryId())
                        .orElse(null);
                if (food != null) {
                    foodInfo.setFoodName(food.getName());
                }
            }
        }
    }


    private void loadBookingInfo(Booking booking) {
        loadSeatInfo(booking);
        loadFoodInfo(booking);
    }
}