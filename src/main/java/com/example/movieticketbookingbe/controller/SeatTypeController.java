package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.SeatType;
import com.example.movieticketbookingbe.service.SeatTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seat-types")
@RequiredArgsConstructor
public class SeatTypeController {
    private final SeatTypeService seatTypeService;

    @PostMapping
    public ResponseEntity<SeatType> createSeatType(@RequestBody SeatType seatType) {
        return ResponseEntity.ok(seatTypeService.createSeatType(seatType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatType> updateSeatType(@PathVariable Long id, @RequestBody SeatType seatType) {
        return ResponseEntity.ok(seatTypeService.updateSeatType(id, seatType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeatType(@PathVariable Long id) {
        seatTypeService.deleteSeatType(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatType> getSeatTypeById(@PathVariable Long id) {
        return seatTypeService.getSeatTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SeatType>> getAllSeatTypes() {
        return ResponseEntity.ok(seatTypeService.getAllSeatTypes());
    }

    @GetMapping("/active")
    public ResponseEntity<List<SeatType>> getActiveSeatTypes() {
        return ResponseEntity.ok(seatTypeService.getActiveSeatTypes());
    }

    @GetMapping("/check-name")
    public ResponseEntity<Boolean> checkNameExists(@RequestParam String name) {
        return ResponseEntity.ok(seatTypeService.existsByName(name));
    }
}