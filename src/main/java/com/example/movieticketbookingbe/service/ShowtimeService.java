package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.model.Showtime;
import com.example.movieticketbookingbe.dto.showtime.ShowtimeCreateDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShowtimeService {
    Showtime createShowtime(ShowtimeCreateDTO createDTO);

    Showtime updateShowtime(Long id, Showtime showtime);

    void deleteShowtime(Long id);

    Optional<Showtime> getShowtimeById(Long id);

    List<Showtime> getAllShowtimes();

    List<Showtime> getActiveShowtimes();
    List<Showtime> getShowtimesByOwner(Long OwnerId);

    List<Showtime> getShowtimesByMovieId(Long movieId);

    List<Showtime> getShowtimesByScreenId(Long screenId);

    List<Showtime> getShowtimesByTheaterId(Long theaterId);

    List<Showtime> getActiveShowtimesByMovieId(Long movieId);

    List<Showtime> getShowtimesByDateRange(LocalDateTime start, LocalDateTime end);

    List<Showtime> getUpcomingShowtimesByMovieId(Long movieId, LocalDateTime dateTime);

    List<Showtime> searchShowtimes(Long movieId, Long theaterId, LocalDateTime startTime, LocalDateTime endTime);

    List<Showtime> filterShowtimesByDateAndMovie(Long movieId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    boolean existsByMovieIdAndScreenIdAndStartTime(Long movieId, Long screenId, LocalDateTime startTime);

    Showtime patchShowtime(Long id, com.example.movieticketbookingbe.dto.showtime.ShowtimePatchDTO patchDTO);
}