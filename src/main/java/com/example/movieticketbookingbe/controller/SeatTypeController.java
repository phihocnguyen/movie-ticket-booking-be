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
import com.example.movieticketbookingbe.dto.seattype.SeatTypePatchDTO;

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
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Tạo loại ghế thành công", dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<SeatTypeDTO>> patchSeatType(
            @PathVariable Long id,
            @RequestBody SeatTypePatchDTO patchDTO) {
        SeatTypeDTO dto = SeatTypeMapper.toDTO(seatTypeService.patchSeatType(id, patchDTO));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Cập nhật loại ghế thành công", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteSeatType(@PathVariable Long id) {
        seatTypeService.deleteSeatType(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Xóa loại ghế thành công", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<SeatTypeDTO>> getSeatTypeById(@PathVariable Long id) {
        return seatTypeService.getSeatTypeById(id)
                .map(seatType -> ResponseEntity.ok(new ApiResponseDTO<>(200, "Tìm thấy loại ghế", SeatTypeMapper.toDTO(seatType))))
                .orElse(ResponseEntity.ok(new ApiResponseDTO<>(404, "Không tìm thấy loại ghế", null)));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<SeatTypeDTO>>> getAllSeatTypes() {
        List<SeatTypeDTO> dtos = seatTypeService.getAllSeatTypes().stream().map(SeatTypeMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy danh sách loại ghế thành công", dtos));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponseDTO<List<SeatTypeDTO>>> getActiveSeatTypes() {
        List<SeatTypeDTO> dtos = seatTypeService.getActiveSeatTypes().stream().map(SeatTypeMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy danh sách loại ghế đang hoạt động thành công", dtos));
    }

    @GetMapping("/check-name")
    public ResponseEntity<Boolean> checkNameExists(@RequestParam String name) {
        return ResponseEntity.ok(seatTypeService.existsByName(name));
    }
}