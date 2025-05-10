package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.Showtime;
import com.example.movieticketbookingbe.repository.ShowtimeRepository;
import com.example.movieticketbookingbe.service.ShowtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShowtimeServiceImpl implements ShowtimeService {
    private final ShowtimeRepository showtimeRepository;

    @Override
    public Showtime createShowtime(Showtime showtime) {
        return showtimeRepository.save(showtime);
    }

    @Override
    public Showtime updateShowtime(Long id, Showtime showtime) {
        Showtime existingShowtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));
        showtime.setId(existingShowtime.getId());
        return showtimeRepository.save(showtime);
    }

    @Override
    public void deleteShowtime(Long id) {
        showtimeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Showtime> getShowtimeById(Long id) {
        return showtimeRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Showtime> getAllShowtimes() {
        return showtimeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Showtime> getActiveShowtimes() {
        return showtimeRepository.findByIsActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Showtime> getShowtimesByMovieId(Long movieId) {
        return showtimeRepository.findByMovieId(movieId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Showtime> getShowtimesByScreenId(Long screenId) {
        return showtimeRepository.findByScreenId(screenId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Showtime> getShowtimesByTheaterId(Long theaterId) {
        return showtimeRepository.findByTheaterId(theaterId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Showtime> getActiveShowtimesByMovieId(Long movieId) {
        return showtimeRepository.findByMovieIdAndIsActiveTrue(movieId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Showtime> getShowtimesByDateRange(LocalDateTime start, LocalDateTime end) {
        return showtimeRepository.findByStartTimeBetween(start, end);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Showtime> getUpcomingShowtimesByMovieId(Long movieId, LocalDateTime dateTime) {
        return showtimeRepository.findByMovieIdAndStartTimeAfter(movieId, dateTime);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Showtime> searchShowtimes(Long movieId, Long theaterId, LocalDateTime startTime,
            LocalDateTime endTime) {
        return showtimeRepository.findByMovieIdAndTheaterIdAndStartTimeBetween(movieId, theaterId, startTime, endTime);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByMovieIdAndScreenIdAndStartTime(Long movieId, Long screenId, LocalDateTime startTime) {
        return showtimeRepository.existsByMovieIdAndScreenIdAndStartTime(movieId, screenId, startTime);
    }
}