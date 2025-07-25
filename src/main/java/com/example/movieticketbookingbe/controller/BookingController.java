package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.dto.booking.BookingRequestDTO;
import com.example.movieticketbookingbe.model.Booking;
import com.example.movieticketbookingbe.model.Booking.BookingSeatInfo;
import com.example.movieticketbookingbe.model.Booking.BookingFoodInfo;
import com.example.movieticketbookingbe.service.BookingService;
import com.example.movieticketbookingbe.dto.booking.BookingDTO;
import com.example.movieticketbookingbe.dto.booking.BookingPatchDTO;
import com.example.movieticketbookingbe.mapper.BookingMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import com.example.movieticketbookingbe.service.PaymentService;
import com.example.movieticketbookingbe.service.VnpayService;
import com.example.movieticketbookingbe.model.Payment;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking", description = "Booking management APIs")
public class BookingController {
    private final BookingService bookingService;
    private final PaymentService paymentService;
    private final VnpayService vnpayService;

    @Value("${vnpay.return-url}")
    private String vnpayReturnUrl;

    @PostMapping
    @Operation(summary = "Create a new booking", description = "Creates a new booking with seats and foods")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking created successfully", content = @Content(schema = @Schema(implementation = Booking.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponseDTO<?>> createBooking(
            @Parameter(description = "Booking request data", required = true) @RequestBody BookingRequestDTO bookingRequest,
            @RequestParam String paymentMethod,
            HttpServletRequest request) {

        //  CHUYỂN DTO sang Booking trong service
        Booking savedBooking = bookingService.createBookingFromDTO(bookingRequest);

        // Tạo payment liên kết
        Payment payment = new Payment();
        payment.setBooking(savedBooking);
        payment.setAmount(savedBooking.getTotalAmount());
        payment.setPaymentMethod(Payment.PaymentMethod.valueOf(paymentMethod));
        payment.setStatus(Payment.PaymentStatus.PENDING.name());
        payment.setIsActive(true);
        Payment savedPayment = paymentService.createPayment(payment);

        if ("VNPAY".equalsIgnoreCase(paymentMethod)) {
            String clientIp = request.getHeader("X-Forwarded-For");
            if (clientIp == null) clientIp = request.getRemoteAddr();
            if ("0:0:0:0:0:0:0:1".equals(clientIp)) clientIp = "127.0.0.1";
            String vnpayUrl = vnpayService.createVnpayPaymentUrl(
                    savedBooking.getId(),
                    savedBooking.getTotalAmount(),
                    vnpayReturnUrl,
                    clientIp
            );
            return ResponseEntity.ok(new ApiResponseDTO<>(200, "Booking created, redirect to VNPAY", vnpayUrl));
        }

        BookingDTO dto = BookingMapper.toDTO(savedBooking);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Booking created successfully", dto));
    }

    @Operation(summary = "Update a booking", description = "Updates an existing booking by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking updated successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<BookingDTO>> patchBooking(
            @Parameter(description = "ID of the booking to update") @PathVariable Long id,
            @RequestBody BookingPatchDTO patchDTO) {
        BookingDTO dto = BookingMapper.toDTO(bookingService.patchBooking(id, patchDTO));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Booking updated successfully", dto));
    }

    @Operation(summary = "Delete a booking", description = "Deletes a booking by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteBooking(
            @Parameter(description = "ID of the booking to delete") @PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Xóa đặt vé thành công", null));
    }

    @Operation(summary = "Get a booking by ID", description = "Returns a booking by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking found", content = @Content(schema = @Schema(implementation = Booking.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<BookingDTO>> getBookingById(
            @Parameter(description = "ID of the booking to retrieve") @PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(booking -> ResponseEntity.ok(new ApiResponseDTO<>(200, "Tìm thấy đặt vé", BookingMapper.toDTO(booking))))
                .orElse(ResponseEntity.ok(new ApiResponseDTO<>(404, "Không tìm thấy đặt vé", null)));
    }

    @Operation(summary = "Get all bookings", description = "Returns a list of all bookings")
    @ApiResponse(responseCode = "200", description = "List of bookings retrieved successfully")
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<BookingDTO>>> getAllBookings() {
        List<BookingDTO> dtos = bookingService.getAllBookings().stream().map(BookingMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy danh sách đặt vé thành công", dtos));
    }

    @Operation(summary = "Get active bookings", description = "Returns a list of all active bookings")
    @ApiResponse(responseCode = "200", description = "List of active bookings retrieved successfully")
    @GetMapping("/active")
    public ResponseEntity<ApiResponseDTO<List<BookingDTO>>> getActiveBookings() {
        List<BookingDTO> dtos = bookingService.getActiveBookings().stream().map(BookingMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy danh sách đặt vé đang hoạt động thành công", dtos));
    }

    @Operation(summary = "Get bookings by user", description = "Returns a list of bookings for a specific user")
    @ApiResponse(responseCode = "200", description = "List of user's bookings retrieved successfully")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDTO<List<BookingDTO>>> getBookingsByUser(
            @Parameter(description = "ID of the user") @PathVariable Long userId) {
        List<BookingDTO> dtos = bookingService.getBookingsByUser(userId).stream().map(BookingMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy danh sách đặt vé theo người dùng thành công", dtos));
    }

    @Operation(summary = "Get bookings by showtime", description = "Returns a list of bookings for a specific showtime")
    @ApiResponse(responseCode = "200", description = "List of showtime bookings retrieved successfully")
    @GetMapping("/showtime/{showtimeId}")
    public ResponseEntity<ApiResponseDTO<List<BookingDTO>>> getBookingsByShowtime(
            @Parameter(description = "ID of the showtime") @PathVariable Long showtimeId) {
        List<BookingDTO> dtos = bookingService.getBookingsByShowtime(showtimeId).stream().map(BookingMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy danh sách đặt vé theo suất chiếu thành công", dtos));
    }

    @Operation(summary = "Search bookings", description = "Search bookings by various criteria")
    @ApiResponse(responseCode = "200", description = "Search results retrieved successfully")
    @GetMapping("/search")
    public ResponseEntity<List<Booking>> searchBookings(
            @Parameter(description = "User ID to filter by") @RequestParam(required = false) Long userId,
            @Parameter(description = "Showtime ID to filter by") @RequestParam(required = false) Long showtimeId,
            @Parameter(description = "Start time for booking search") @RequestParam(required = false) LocalDateTime startTime,
            @Parameter(description = "End time for booking search") @RequestParam(required = false) LocalDateTime endTime) {
        return ResponseEntity.ok(bookingService.searchBookings(userId, showtimeId, startTime, endTime));
    }

    @Operation(summary = "Cancel a booking", description = "Cancels an existing booking")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancelBooking(
            @Parameter(description = "ID of the booking to cancel") @PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    @Operation(summary = "Complete a booking", description = "Marks a booking as completed")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking completed successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @PutMapping("/{id}/complete")
    public ResponseEntity<Booking> completeBooking(
            @Parameter(description = "ID of the booking to complete") @PathVariable Long id) {
        return ResponseEntity.ok(bookingService.completeBooking(id));
    }
    @Operation(summary = "Get all bookings by owner", description = "Returns all bookings across all cinemas owned by a specific owner")
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<ApiResponseDTO<List<BookingDTO>>> getBookingsByOwner(
            @Parameter(description = "ID of the owner") @PathVariable Long ownerId) {
        List<BookingDTO> dtos = bookingService.getBookingsByOwner(ownerId).stream().map(BookingMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy danh sách đặt vé theo owner thành công", dtos));
    }
}