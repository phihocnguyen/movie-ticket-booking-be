package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.model.Movie;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovieService {
    Movie createMovie(Movie movie);

    Movie updateMovie(Long id, Movie movie);

    void deleteMovie(Long id);

    Optional<Movie> getMovieById(Long id);

    List<Movie> getAllMovies();

    List<Movie> getMoviesByReleaseDateAfter(LocalDate date);

    List<Movie> getMoviesByGenre(String genre);

    List<Movie> getMoviesByLanguage(String language);

    List<Movie> searchMoviesByTitle(String title);

    List<Movie> searchMovies(String title, String genre, String language);

    List<Movie> getRandomMovies();

    List<Movie> getLatestMovies();

    List<Movie> getTopRatedMovies();
}