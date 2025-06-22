package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.Payment;
import com.example.movieticketbookingbe.service.PaymentService;
import com.example.movieticketbookingbe.dto.payment.PaymentDTO;
import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import com.example.movieticketbookingbe.mapper.PaymentMapper;
import com.example.movieticketbookingbe.dto.payment.PaymentCreateDTO;
import com.example.movieticketbookingbe.dto.payment.PaymentPatchDTO;
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

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Payment management APIs")
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "Create a new payment", description = "Creates a new payment with the provided information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment created successfully", content = @Content(schema = @Schema(implementation = Payment.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDTO<PaymentDTO>> createPayment(@RequestBody PaymentCreateDTO paymentCreateDTO) {
        Payment payment = PaymentMapper.toEntity(paymentCreateDTO);
        PaymentDTO dto = PaymentMapper.toDTO(paymentService.createPayment(payment));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Payment created successfully", dto));
    }

    @Operation(summary = "Update a payment", description = "Updates an existing payment by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PaymentDTO>> patchPayment(
            @Parameter(description = "ID of the payment to update") @PathVariable Long id,
            @RequestBody PaymentPatchDTO patchDTO) {
        PaymentDTO dto = PaymentMapper.toDTO(paymentService.patchPayment(id, patchDTO));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Payment updated successfully", dto));
    }

    @Operation(summary = "Delete a payment", description = "Deletes a payment by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deletePayment(
            @Parameter(description = "ID of the payment to delete") @PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Xóa thanh toán thành công", null));
    }

    @Operation(summary = "Get a payment by ID", description = "Returns a payment by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment found", content = @Content(schema = @Schema(implementation = Payment.class))),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PaymentDTO>> getPaymentById(
            @Parameter(description = "ID of the payment to retrieve") @PathVariable Long id) {
        return paymentService.getPaymentById(id)
                .map(payment -> ResponseEntity.ok(new ApiResponseDTO<>(200, "Tìm thấy thanh toán", PaymentMapper.toDTO(payment))))
                .orElse(ResponseEntity.ok(new ApiResponseDTO<>(404, "Không tìm thấy thanh toán", null)));
    }

    @Operation(summary = "Get all payments", description = "Returns a list of all payments")
    @ApiResponse(responseCode = "200", description = "List of payments retrieved successfully")
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<PaymentDTO>>> getAllPayments() {
        List<PaymentDTO> dtos = paymentService.getAllPayments().stream().map(PaymentMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy danh sách thanh toán thành công", dtos));
    }

    @Operation(summary = "Get payment by booking", description = "Returns the payment for a specific booking")
    @ApiResponse(responseCode = "200", description = "Payment retrieved successfully")
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<ApiResponseDTO<PaymentDTO>> getPaymentByBooking(
            @Parameter(description = "ID of the booking") @PathVariable Long bookingId) {
        PaymentDTO dto = PaymentMapper.toDTO(paymentService.getPaymentByBooking(bookingId));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Payment retrieved successfully", dto));
    }

    @Operation(summary = "Complete a payment", description = "Marks a payment as completed")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment completed successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @PutMapping("/{id}/complete")
    public ResponseEntity<ApiResponseDTO<Payment>> completePayment(
            @Parameter(description = "ID of the payment to complete") @PathVariable Long id) {
        Payment payment = paymentService.completePayment(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Payment completed successfully", payment));
    }

    @Operation(summary = "Fail a payment", description = "Marks a payment as failed")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment marked as failed successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @PutMapping("/{id}/fail")
    public ResponseEntity<ApiResponseDTO<Payment>> failPayment(
            @Parameter(description = "ID of the payment to fail") @PathVariable Long id) {
        Payment payment = paymentService.failPayment(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Payment marked as failed successfully", payment));
    }

    @Operation(summary = "Refund a payment", description = "Processes a refund for a payment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment refunded successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @PutMapping("/{id}/refund")
    public ResponseEntity<ApiResponseDTO<Payment>> refundPayment(
            @Parameter(description = "ID of the payment to refund") @PathVariable Long id) {
        Payment payment = paymentService.refundPayment(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Payment refunded successfully", payment));
    }
}