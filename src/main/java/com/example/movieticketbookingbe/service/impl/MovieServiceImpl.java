package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.Movie;
import com.example.movieticketbookingbe.repository.MovieRepository;
import com.example.movieticketbookingbe.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(Long id, Movie movie) {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setId(existingMovie.getId());
        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> getMoviesByReleaseDateAfter(LocalDate date) {
        return movieRepository.findByReleaseDateAfter(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> getMoviesByLanguage(String language) {
        return movieRepository.findByLanguage(language);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> searchMoviesByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> searchMovies(String title, String genre, String language) {
        return movieRepository.findByTitleContainingIgnoreCaseAndGenreAndLanguage(title, genre, language);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> getRandomMovies() {
        return movieRepository.findRandomMovies();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> getLatestMovies() {
        return movieRepository.findLatestMovies();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> getTopRatedMovies() {
        return movieRepository.findTopRatedMovies();
    }
}