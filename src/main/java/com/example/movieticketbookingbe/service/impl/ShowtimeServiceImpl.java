package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.*;
import com.example.movieticketbookingbe.repository.*;
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
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final TheaterRepository theaterRepository;

    @Override
    public Showtime createShowtime(Showtime showtime) {
        // Validate movie exists
        Movie movie = movieRepository.findById(showtime.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // Validate theater exists
        Theater theater = theaterRepository.findById(showtime.getTheaterId())
                .orElseThrow(() -> new RuntimeException("Theater not found"));

        // Validate screen exists and belongs to the specified theater
        Screen screen = screenRepository.findById(showtime.getScreenId())
                .orElseThrow(() -> new RuntimeException("Screen not found"));

        if (!screen.getTheater().getId().equals(showtime.getTheaterId())) {
            throw new RuntimeException("Screen does not belong to the specified theater");
        }

        // Validate time conflicts
        validateTimeConflicts(showtime);

        showtime.setMovie(movie);
        showtime.setTheater(theater);
        showtime.setScreen(screen);
        return showtimeRepository.save(showtime);
    }

    @Override
    public Showtime updateShowtime(Long id, Showtime showtimeDetails) {
        Showtime showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        // Validate movie exists
        Movie movie = movieRepository.findById(showtimeDetails.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // Validate theater exists
        Theater theater = theaterRepository.findById(showtimeDetails.getTheaterId())
                .orElseThrow(() -> new RuntimeException("Theater not found"));

        // Validate screen exists and belongs to the specified theater
        Screen screen = screenRepository.findById(showtimeDetails.getScreenId())
                .orElseThrow(() -> new RuntimeException("Screen not found"));

        if (!screen.getTheater().getId().equals(showtimeDetails.getTheaterId())) {
            throw new RuntimeException("Screen does not belong to the specified theater");
        }

        // Validate time conflicts (excluding current showtime)
        validateTimeConflictsForUpdate(showtimeDetails, id);

        showtime.setMovie(movie);
        showtime.setTheater(theater);
        showtime.setScreen(screen);
        showtime.setStartTime(showtimeDetails.getStartTime());
        showtime.setEndTime(showtimeDetails.getEndTime());
        showtime.setPrice(showtimeDetails.getPrice());
        showtime.setIsActive(showtimeDetails.getIsActive());

        return showtimeRepository.save(showtime);
    }

    @Override
    public void deleteShowtime(Long id) {
        Showtime showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));
        showtimeRepository.delete(showtime);
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
        return showtimeRepository.findByMovieIdAndIsActiveTrue(movieId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Showtime> getShowtimesByScreenId(Long screenId) {
        return showtimeRepository.findByScreenIdAndIsActiveTrue(screenId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Showtime> getShowtimesByTheaterId(Long theaterId) {
        return showtimeRepository.findByTheaterIdAndIsActiveTrue(theaterId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Showtime> getActiveShowtimesByMovieId(Long movieId) {
        return showtimeRepository.findByMovieIdAndIsActiveTrue(movieId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Showtime> getShowtimesByDateRange(LocalDateTime start, LocalDateTime end) {
        return showtimeRepository.findByStartTimeBetweenAndIsActiveTrue(start, end);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Showtime> getUpcomingShowtimesByMovieId(Long movieId, LocalDateTime dateTime) {
        return showtimeRepository.findByMovieIdAndStartTimeAfterAndIsActiveTrue(movieId, dateTime);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Showtime> searchShowtimes(Long movieId, Long theaterId, LocalDateTime startTime,
            LocalDateTime endTime) {
        return showtimeRepository.findByMovieIdAndTheaterIdAndStartTimeBetweenAndIsActiveTrue(movieId, theaterId,
                startTime, endTime);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByMovieIdAndScreenIdAndStartTime(Long movieId, Long screenId, LocalDateTime startTime) {
        return showtimeRepository.existsByMovieIdAndScreenIdAndStartTime(movieId, screenId, startTime);
    }

    @Override
    public Showtime patchShowtime(Long id, ShowtimePatchDTO patchDTO) {
        Showtime showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));
        if (patchDTO.getStartTime() != null) showtime.setStartTime(patchDTO.getStartTime());
        if (patchDTO.getEndTime() != null) showtime.setEndTime(patchDTO.getEndTime());
        if (patchDTO.getPrice() != null) showtime.setPrice(patchDTO.getPrice());
        if (patchDTO.getIsActive() != null) showtime.setIsActive(patchDTO.getIsActive());
        if (patchDTO.getMovieId() != null) {
            // TODO: set movie entity nếu cần
        }
        if (patchDTO.getScreenId() != null) {
            // TODO: set screen entity nếu cần
        }
        return showtimeRepository.save(showtime);
    }

    private void validateTimeConflicts(Showtime showtime) {
        List<Showtime> existingShowtimes = showtimeRepository.findByScreenIdAndIsActiveTrue(showtime.getScreenId());
        for (Showtime existing : existingShowtimes) {
            if (isTimeOverlap(showtime.getStartTime(), showtime.getEndTime(),
                    existing.getStartTime(), existing.getEndTime())) {
                throw new RuntimeException("Time conflict with existing showtime");
            }
        }
    }

    private void validateTimeConflictsForUpdate(Showtime showtime, Long currentId) {
        List<Showtime> existingShowtimes = showtimeRepository.findByScreenIdAndIsActiveTrue(showtime.getScreenId());
        for (Showtime existing : existingShowtimes) {
            if (!existing.getId().equals(currentId) &&
                    isTimeOverlap(showtime.getStartTime(), showtime.getEndTime(),
                            existing.getStartTime(), existing.getEndTime())) {
                throw new RuntimeException("Time conflict with existing showtime");
            }
        }
    }

    private boolean isTimeOverlap(LocalDateTime start1, LocalDateTime end1,
            LocalDateTime start2, LocalDateTime end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }
}