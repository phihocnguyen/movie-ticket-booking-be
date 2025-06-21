package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.SeatType;
import com.example.movieticketbookingbe.service.SeatTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.movieticketbookingbe.dto.seattype.SeatTypeCreateDTO;
import com.example.movieticketbookingbe.dto.SeatTypeDTO;
import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import com.example.movieticketbookingbe.mapper.SeatTypeMapper;

import java.util.List;

@RestController
@RequestMapping("/api/seat-types")
@RequiredArgsConstructor
public class SeatTypeController {
    private final SeatTypeService seatTypeService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<SeatTypeDTO>> createSeatType(@RequestBody SeatTypeCreateDTO seatTypeCreateDTO) {
        SeatType seatType = SeatTypeMapper.toEntity(seatTypeCreateDTO);
        SeatTypeDTO dto = SeatTypeMapper.toDTO(seatTypeService.createSeatType(seatType));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Seat type created successfully", dto));
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