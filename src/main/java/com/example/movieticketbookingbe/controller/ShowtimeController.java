package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.Showtime;
import com.example.movieticketbookingbe.service.ShowtimeService;
import com.example.movieticketbookingbe.dto.showtime.ShowtimeDTO;
import com.example.movieticketbookingbe.dto.showtime.ShowtimeCreateDTO;
import com.example.movieticketbookingbe.dto.showtime.ShowtimePatchDTO;
import com.example.movieticketbookingbe.mapper.ShowtimeMapper;
import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
@RequiredArgsConstructor
public class ShowtimeController {
    private final ShowtimeService showtimeService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<ShowtimeDTO>> createShowtime(@RequestBody ShowtimeCreateDTO createDTO) {
        ShowtimeDTO dto = ShowtimeMapper.toDTO(showtimeService.createShowtime(createDTO));
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
                .map(showtime -> ResponseEntity.ok(new ApiResponseDTO<>(200, "Showtime retrieved successfully", ShowtimeMapper.toDTO(showtime))))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ShowtimeDTO>>> getAllShowtimes() {
        List<ShowtimeDTO> dtos = showtimeService.getAllShowtimes().stream().map(ShowtimeMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Showtimes retrieved successfully", dtos));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponseDTO<List<ShowtimeDTO>>> getActiveShowtimes() {
        List<ShowtimeDTO> dtos = showtimeService.getActiveShowtimes().stream().map(ShowtimeMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Active showtimes retrieved successfully", dtos));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponseDTO<List<ShowtimeDTO>>> getShowtimesByMovieId(@PathVariable Long movieId) {
        List<ShowtimeDTO> dtos = showtimeService.getShowtimesByMovieId(movieId).stream().map(ShowtimeMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Showtimes retrieved successfully", dtos));
    }

    @GetMapping("/screen/{screenId}")
    public ResponseEntity<ApiResponseDTO<List<ShowtimeDTO>>> getShowtimesByScreenId(@PathVariable Long screenId) {
        List<ShowtimeDTO> dtos = showtimeService.getShowtimesByScreenId(screenId).stream().map(ShowtimeMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Showtimes retrieved successfully", dtos));
    }

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<ApiResponseDTO<List<ShowtimeDTO>>> getShowtimesByTheaterId(@PathVariable Long theaterId) {
        List<ShowtimeDTO> dtos = showtimeService.getShowtimesByTheaterId(theaterId).stream().map(ShowtimeMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Showtimes retrieved successfully", dtos));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponseDTO<List<ShowtimeDTO>>> filterShowtimesByDateAndMovie(
            @RequestParam(required = false) Long movieId,
            @RequestParam(required = false) String date) {
        
        LocalDateTime startOfDay = null;
        LocalDateTime endOfDay = null;
        
        if (date != null && !date.trim().isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.parse(date, formatter);
                startOfDay = localDate.atStartOfDay();
                endOfDay = localDate.atTime(23, 59, 59);
            } catch (Exception e) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponseDTO<>(400, "Invalid date format. Use dd/MM/yyyy", null));
            }
        }
        
        List<ShowtimeDTO> dtos = showtimeService.filterShowtimesByDateAndMovie(movieId, startOfDay, endOfDay)
            .stream().map(ShowtimeMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Showtimes filtered successfully", dtos));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponseDTO<List<ShowtimeDTO>>> searchShowtimes(
            @RequestParam(required = false) Long movieId,
            @RequestParam(required = false) Long theaterId,
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime) {
        List<ShowtimeDTO> dtos = showtimeService.searchShowtimes(movieId, theaterId, startTime, endTime).stream().map(ShowtimeMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Showtimes retrieved successfully", dtos));
    }
}