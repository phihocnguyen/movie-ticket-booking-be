package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.Showtime;
import com.example.movieticketbookingbe.service.ShowtimeService;
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
    public ResponseEntity<Showtime> createShowtime(@RequestBody Showtime showtime) {
        return ResponseEntity.ok(showtimeService.createShowtime(showtime));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Showtime> updateShowtime(@PathVariable Long id, @RequestBody Showtime showtime) {
        return ResponseEntity.ok(showtimeService.updateShowtime(id, showtime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long id) {
        showtimeService.deleteShowtime(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Showtime> getShowtimeById(@PathVariable Long id) {
        return showtimeService.getShowtimeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Showtime>> getAllShowtimes() {
        return ResponseEntity.ok(showtimeService.getAllShowtimes());
    }

    @GetMapping("/active")
    public ResponseEntity<List<Showtime>> getActiveShowtimes() {
        return ResponseEntity.ok(showtimeService.getActiveShowtimes());
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Showtime>> getShowtimesByMovieId(@PathVariable Long movieId) {
        return ResponseEntity.ok(showtimeService.getShowtimesByMovieId(movieId));
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