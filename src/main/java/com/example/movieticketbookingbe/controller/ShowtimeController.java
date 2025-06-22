package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.Showtime;
import com.example.movieticketbookingbe.service.ShowtimeService;
import com.example.movieticketbookingbe.dto.ShowtimeDTO;
import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import com.example.movieticketbookingbe.dto.showtime.ShowtimePatchDTO;
import com.example.movieticketbookingbe.mapper.ShowtimeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
@RequiredArgsConstructor
public class ShowtimeController {
    private final ShowtimeService showtimeService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<ShowtimeDTO>> createShowtime(@RequestBody Showtime showtime) {
        ShowtimeDTO dto = ShowtimeMapper.toDTO(showtimeService.createShowtime(showtime));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Showtime created successfully", dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ShowtimeDTO>> patchShowtime(
            @PathVariable Long id,
            @RequestBody ShowtimePatchDTO patchDTO) {
        ShowtimeDTO dto = ShowtimeMapper.toDTO(showtimeService.patchShowtime(id, patchDTO));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Showtime updated successfully", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteShowtime(@PathVariable Long id) {
        showtimeService.deleteShowtime(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Showtime deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ShowtimeDTO>> getShowtimeById(@PathVariable Long id) {
        return showtimeService.getShowtimeById(id)
                .map(showtime -> ResponseEntity.ok(new ApiResponseDTO<>(200, "Showtime found", ShowtimeMapper.toDTO(showtime))))
                .orElse(ResponseEntity.ok(new ApiResponseDTO<>(404, "Showtime not found", null)));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ShowtimeDTO>>> getAllShowtimes() {
        List<ShowtimeDTO> dtos = showtimeService.getAllShowtimes().stream().map(ShowtimeMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of showtimes retrieved successfully", dtos));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponseDTO<List<ShowtimeDTO>>> getActiveShowtimes() {
        List<ShowtimeDTO> dtos = showtimeService.getActiveShowtimes().stream().map(ShowtimeMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of active showtimes retrieved successfully", dtos));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponseDTO<List<ShowtimeDTO>>> getShowtimesByMovieId(@PathVariable Long movieId) {
        List<ShowtimeDTO> dtos = showtimeService.getShowtimesByMovieId(movieId).stream().map(ShowtimeMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "List of movie's showtimes retrieved successfully", dtos));
    }

    @GetMapping("/screen/{screenId}")
    public ResponseEntity<List<Showtime>> getShowtimesByScreenId(@PathVariable Long screenId) {
        return ResponseEntity.ok(showtimeService.getShowtimesByScreenId(screenId));
    }

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<Showtime>> getShowtimesByTheaterId(@PathVariable Long theaterId) {
        return ResponseEntity.ok(showtimeService.getShowtimesByTheaterId(theaterId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Showtime>> searchShowtimes(
            @RequestParam(required = false) Long movieId,
            @RequestParam(required = false) Long theaterId,
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime) {
        return ResponseEntity.ok(showtimeService.searchShowtimes(movieId, theaterId, startTime, endTime));
    }
}