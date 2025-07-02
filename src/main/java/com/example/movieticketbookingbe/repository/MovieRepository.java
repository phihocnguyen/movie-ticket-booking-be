package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByReleaseDateAfter(LocalDate date);

    List<Movie> findByTitleContainingIgnoreCaseAndGenreAndLanguage(String title, String genre, String language);

    List<Movie> findByGenre(String genre);

    List<Movie> findByLanguage(String language);

    List<Movie> findByTitleContainingIgnoreCase(String title);

    @Query(value = "SELECT * FROM movies ORDER BY RANDOM() LIMIT 3", nativeQuery = true)
    List<Movie> findRandomMovies();

    @Query(value = "SELECT * FROM movies ORDER BY release_date DESC LIMIT 10", nativeQuery = true)
    List<Movie> findLatestMovies();

    @Query(value = "SELECT * FROM movies ORDER BY rating DESC LIMIT 10", nativeQuery = true)
    List<Movie> findTopRatedMovies();
    long countByIsActiveTrue();
}