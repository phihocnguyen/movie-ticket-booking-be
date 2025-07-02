package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
        List<Showtime> findByMovieId(Long movieId);

        List<Showtime> findByScreenId(Long screenId);

        @Query("SELECT s FROM Showtime s WHERE s.screen.theater.id = :theaterId")
        List<Showtime> findByTheaterId(Long theaterId);

        List<Showtime> findByIsActiveTrue();

        @Query("SELECT s FROM Showtime s WHERE s.movie.id = :movieId AND s.screen.theater.id = :theaterId AND s.startTime BETWEEN :startTime AND :endTime")
        List<Showtime> findByMovieIdAndTheaterIdAndStartTimeBetween(Long movieId, Long theaterId,
                        LocalDateTime startTime,
                        LocalDateTime endTime);

        boolean existsByMovieIdAndScreenIdAndStartTime(Long movieId, Long screenId, LocalDateTime startTime);

        List<Showtime> findByMovieIdAndIsActiveTrue(Long movieId);

        List<Showtime> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

        List<Showtime> findByMovieIdAndStartTimeAfter(Long movieId, LocalDateTime dateTime);

        List<Showtime> findByTheaterIdAndIsActiveTrue(Long theaterId);

        List<Showtime> findByScreenIdAndIsActiveTrue(Long screenId);

        List<Showtime> findByStartTimeBetweenAndIsActiveTrue(LocalDateTime start, LocalDateTime end);

        List<Showtime> findByMovieIdAndStartTimeAfterAndIsActiveTrue(Long movieId, LocalDateTime dateTime);

        List<Showtime> findByMovieIdAndStartTimeBetweenAndIsActiveTrue(Long movieId, LocalDateTime startTime, LocalDateTime endTime);

        List<Showtime> findByMovieIdAndTheaterIdAndStartTimeBetweenAndIsActiveTrue(
                        Long movieId, Long theaterId, LocalDateTime startTime, LocalDateTime endTime);

        List<Showtime> findByTheater_TheaterOwner_IdAndIsActiveTrue(Long theaterOwnerId);

        @Query("SELECT COUNT(s.id) FROM Showtime s WHERE s.theater.theaterOwnerId = :ownerId")
        Long getTotalShowtimesByOwner(Long ownerId);
}