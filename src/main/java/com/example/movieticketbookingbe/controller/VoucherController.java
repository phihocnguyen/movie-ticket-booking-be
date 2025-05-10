package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.Voucher;
import com.example.movieticketbookingbe.service.VoucherService;
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
@RequestMapping("/api/vouchers")
@RequiredArgsConstructor
@Tag(name = "Voucher", description = "Voucher management APIs")
public class VoucherController {
    private final VoucherService voucherService;

    @Operation(summary = "Create a new voucher", description = "Creates a new voucher with the provided information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Voucher created successfully", content = @Content(schema = @Schema(implementation = Voucher.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Voucher> createVoucher(@RequestBody Voucher voucher) {
        return ResponseEntity.ok(voucherService.createVoucher(voucher));
    }

    @Operation(summary = "Update a voucher", description = "Updates an existing voucher by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Voucher updated successfully"),
            @ApiResponse(responseCode = "404", description = "Voucher not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Voucher> updateVoucher(
            @Parameter(description = "ID of the voucher to update") @PathVariable Long id,
            @RequestBody Voucher voucher) {
        return ResponseEntity.ok(voucherService.updateVoucher(id, voucher));
    }

    @Operation(summary = "Delete a voucher", description = "Deletes a voucher by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Voucher deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Voucher not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(
            @Parameter(description = "ID of the voucher to delete") @PathVariable Long id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get a voucher by ID", description = "Returns a voucher by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Voucher found", content = @Content(schema = @Schema(implementation = Voucher.class))),
            @ApiResponse(responseCode = "404", description = "Voucher not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Voucher> getVoucherById(
            @Parameter(description = "ID of the voucher to retrieve") @PathVariable Long id) {
        return voucherService.getVoucherById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all vouchers", description = "Returns a list of all vouchers")
    @ApiResponse(responseCode = "200", description = "List of vouchers retrieved successfully")
    @GetMapping
    public ResponseEntity<List<Voucher>> getAllVouchers() {
        return ResponseEntity.ok(voucherService.getAllVouchers());
    }

    @Operation(summary = "Get active vouchers", description = "Returns a list of all active vouchers")
    @ApiResponse(responseCode = "200", description = "List of active vouchers retrieved successfully")
    @GetMapping("/active")
    public ResponseEntity<List<Voucher>> getActiveVouchers() {
        return ResponseEntity.ok(voucherService.getActiveVouchers());
    }

    @Operation(summary = "Get valid vouchers", description = "Returns a list of all valid vouchers")
    @ApiResponse(responseCode = "200", description = "List of valid vouchers retrieved successfully")
    @GetMapping("/valid")
    public ResponseEntity<List<Voucher>> getValidVouchers() {
        return ResponseEntity.ok(voucherService.getValidVouchers());
    }

    @Operation(summary = "Search vouchers", description = "Search vouchers by code")
    @ApiResponse(responseCode = "200", description = "Search results retrieved successfully")
    @GetMapping("/search")
    public ResponseEntity<List<Voucher>> searchVouchers(
            @Parameter(description = "Voucher code to search for") @RequestParam String code) {
        return ResponseEntity.ok(voucherService.searchVouchers(code));
    }

    @Operation(summary = "Get voucher by code", description = "Returns a voucher by its code")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Voucher found", content = @Content(schema = @Schema(implementation = Voucher.class))),
            @ApiResponse(responseCode = "404", description = "Voucher not found")
    })
    @GetMapping("/code/{code}")
    public ResponseEntity<Voucher> getVoucherByCode(
            @Parameter(description = "Code of the voucher to retrieve") @PathVariable String code) {
        return voucherService.getVoucherByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}