package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.*;
import com.example.movieticketbookingbe.repository.*;
import com.example.movieticketbookingbe.service.ShowtimeService;
import com.example.movieticketbookingbe.dto.showtime.ShowtimePatchDTO;
import com.example.movieticketbookingbe.dto.showtime.ShowtimeCreateDTO;
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
    public Showtime createShowtime(ShowtimeCreateDTO createDTO) {
        // Validate movie exists
        Movie movie = movieRepository.findById(createDTO.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // Validate theater exists
        Theater theater = theaterRepository.findById(createDTO.getTheaterId())
                .orElseThrow(() -> new RuntimeException("Theater not found"));

        // Validate screen exists and belongs to the specified theater
        Screen screen = screenRepository.findById(createDTO.getScreenId())
                .orElseThrow(() -> new RuntimeException("Screen not found"));

        if (!screen.getTheater().getId().equals(createDTO.getTheaterId())) {
            throw new RuntimeException("Screen does not belong to the specified theater");
        }

        // Validate time conflicts
        Showtime showtime = new Showtime();
        showtime.setMovieId(createDTO.getMovieId());
        showtime.setTheaterId(createDTO.getTheaterId());
        showtime.setScreenId(createDTO.getScreenId());
        showtime.setStartTime(createDTO.getStartTime());
        showtime.setEndTime(createDTO.getEndTime());
        showtime.setPrice(createDTO.getPrice());
        showtime.setIsActive(createDTO.getIsActive());

        // Set timestamps
        LocalDateTime now = LocalDateTime.now();
        showtime.setCreatedAt(now);
        showtime.setUpdatedAt(now);

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

        // Update timestamp
        showtime.setUpdatedAt(LocalDateTime.now());

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
    public List<Showtime> getShowtimesByOwner(Long OwnerId) {
        return showtimeRepository.findByTheater_TheaterOwner_IdAndIsActiveTrue(OwnerId);
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
    public List<Showtime> filterShowtimesByDateAndMovie(Long movieId, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        if (movieId != null && startOfDay != null && endOfDay != null) {
            // Filter by both movieId and date range
            return showtimeRepository.findByMovieIdAndStartTimeBetweenAndIsActiveTrue(movieId, startOfDay, endOfDay);
        } else if (movieId != null) {
            // Filter by movieId only
            return showtimeRepository.findByMovieIdAndIsActiveTrue(movieId);
        } else if (startOfDay != null && endOfDay != null) {
            // Filter by date range only
            return showtimeRepository.findByStartTimeBetweenAndIsActiveTrue(startOfDay, endOfDay);
        } else {
            // No filters, return all active showtimes
            return showtimeRepository.findByIsActiveTrue();
        }
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
        if (patchDTO.getPrice() != null) showtime.setPrice(Double.valueOf(patchDTO.getPrice()));
        if (patchDTO.getIsActive() != null) showtime.setIsActive(patchDTO.getIsActive());
        if (patchDTO.getMovieId() != null) {
            Movie movie = movieRepository.findById(patchDTO.getMovieId())
                    .orElseThrow(() -> new RuntimeException("Movie not found"));
            showtime.setMovie(movie);
        }

        if (patchDTO.getScreenId() != null) {
            Screen screen = screenRepository.findById(patchDTO.getScreenId())
                    .orElseThrow(() -> new RuntimeException("Screen not found"));
            showtime.setScreen(screen);
        }

        if (patchDTO.getTheaterId() != null) {
            Theater theater = theaterRepository.findById(patchDTO.getTheaterId())
                    .orElseThrow(() -> new RuntimeException("Theater not found"));
            showtime.setTheater(theater);
        }


        
        // Update timestamp
        showtime.setUpdatedAt(LocalDateTime.now());
        
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