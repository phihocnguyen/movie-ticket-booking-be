package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.model.Showtime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShowtimeService {
    Showtime createShowtime(Showtime showtime);

    Showtime updateShowtime(Long id, Showtime showtime);

    void deleteShowtime(Long id);

    Optional<Showtime> getShowtimeById(Long id);

    List<Showtime> getAllShowtimes();

    List<Showtime> getActiveShowtimes();

    List<Showtime> getShowtimesByMovieId(Long movieId);

    List<Showtime> getShowtimesByScreenId(Long screenId);

    List<Showtime> getShowtimesByTheaterId(Long theaterId);

    List<Showtime> getActiveShowtimesByMovieId(Long movieId);

    List<Showtime> getShowtimesByDateRange(LocalDateTime start, LocalDateTime end);

    List<Showtime> getUpcomingShowtimesByMovieId(Long movieId, LocalDateTime dateTime);

    List<Showtime> searchShowtimes(Long movieId, Long theaterId, LocalDateTime startTime, LocalDateTime endTime);

    boolean existsByMovieIdAndScreenIdAndStartTime(Long movieId, Long screenId, LocalDateTime startTime);

    Showtime patchShowtime(Long id, com.example.movieticketbookingbe.dto.showtime.ShowtimePatchDTO patchDTO);
}