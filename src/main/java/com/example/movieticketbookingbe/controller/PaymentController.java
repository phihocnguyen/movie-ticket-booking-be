package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.Payment;
import com.example.movieticketbookingbe.service.PaymentService;
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
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.createPayment(payment));
    }

    @Operation(summary = "Update a payment", description = "Updates an existing payment by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(
            @Parameter(description = "ID of the payment to update") @PathVariable Long id,
            @RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.updatePayment(id, payment));
    }

    @Operation(summary = "Delete a payment", description = "Deletes a payment by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(
            @Parameter(description = "ID of the payment to delete") @PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get a payment by ID", description = "Returns a payment by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment found", content = @Content(schema = @Schema(implementation = Payment.class))),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(
            @Parameter(description = "ID of the payment to retrieve") @PathVariable Long id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all payments", description = "Returns a list of all payments")
    @ApiResponse(responseCode = "200", description = "List of payments retrieved successfully")
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @Operation(summary = "Get payment by booking", description = "Returns the payment for a specific booking")
    @ApiResponse(responseCode = "200", description = "Payment retrieved successfully")
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<Payment> getPaymentByBooking(
            @Parameter(description = "ID of the booking") @PathVariable Long bookingId) {
        return ResponseEntity.ok(paymentService.getPaymentByBooking(bookingId));
    }

    @Operation(summary = "Complete a payment", description = "Marks a payment as completed")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment completed successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @PutMapping("/{id}/complete")
    public ResponseEntity<Payment> completePayment(
            @Parameter(description = "ID of the payment to complete") @PathVariable Long id) {
        return ResponseEntity.ok(paymentService.completePayment(id));
    }

    @Operation(summary = "Fail a payment", description = "Marks a payment as failed")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment marked as failed successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @PutMapping("/{id}/fail")
    public ResponseEntity<Payment> failPayment(
            @Parameter(description = "ID of the payment to fail") @PathVariable Long id) {
        return ResponseEntity.ok(paymentService.failPayment(id));
    }

    @Operation(summary = "Refund a payment", description = "Processes a refund for a payment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment refunded successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @PutMapping("/{id}/refund")
    public ResponseEntity<Payment> refundPayment(
            @Parameter(description = "ID of the payment to refund") @PathVariable Long id) {
        return ResponseEntity.ok(paymentService.refundPayment(id));
    }
}