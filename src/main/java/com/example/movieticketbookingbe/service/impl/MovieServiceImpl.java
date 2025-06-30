package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.Movie;
import com.example.movieticketbookingbe.repository.MovieRepository;
import com.example.movieticketbookingbe.service.MovieService;
import com.example.movieticketbookingbe.dto.movie.MoviePatchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.movieticketbookingbe.event.MovieCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate; 

    @Override
    public Movie createMovie(Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        System.out.println("[MovieServiceImpl] Saved movie: " + savedMovie);
        MovieCreatedEvent event = new MovieCreatedEvent(savedMovie.getId(), savedMovie.getTitle());
        
        kafkaTemplate.send("movie-created-v2", event);
        System.out.println("[MovieServiceImpl] Published event to Kafka: " + event);
        return savedMovie;
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

    @Override
    public Movie patchMovie(Long id, MoviePatchDTO patchDTO) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        if (patchDTO.getTitle() != null) movie.setTitle(patchDTO.getTitle());
        if (patchDTO.getTitleVi() != null) movie.setTitleVi(patchDTO.getTitleVi());
        if (patchDTO.getDescription() != null) movie.setDescription(patchDTO.getDescription());
        if (patchDTO.getDuration() != null) movie.setDuration(patchDTO.getDuration());
        if (patchDTO.getLanguage() != null) movie.setLanguage(patchDTO.getLanguage());
        if (patchDTO.getGenre() != null) movie.setGenre(patchDTO.getGenre());
        if (patchDTO.getReleaseDate() != null) movie.setReleaseDate(patchDTO.getReleaseDate());
        if (patchDTO.getPosterUrl() != null) movie.setPosterUrl(patchDTO.getPosterUrl());
        if (patchDTO.getBackdropUrl() != null) movie.setBackdropUrl(patchDTO.getBackdropUrl());
        if (patchDTO.getTrailerUrl() != null) movie.setTrailerUrl(patchDTO.getTrailerUrl());
        if (patchDTO.getDirector() != null) movie.setDirector(patchDTO.getDirector());
        if (patchDTO.getActor() != null) movie.setActor(patchDTO.getActor());
        if (patchDTO.getRating() != null) movie.setRating(patchDTO.getRating());
        if (patchDTO.getCountry() != null) movie.setCountry(patchDTO.getCountry());
        if (patchDTO.getIsActive() != null) movie.setIsActive(patchDTO.getIsActive());
        return movieRepository.save(movie);
    }
}