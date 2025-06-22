package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.MovieDTO;
import com.example.movieticketbookingbe.model.Movie;
import com.example.movieticketbookingbe.dto.movie.MovieCreateDTO;
import com.example.movieticketbookingbe.dto.showtime.ShowtimeDTO;
import java.util.stream.Collectors;

public class MovieMapper {
    public static MovieDTO toDTO(Movie movie) {
        if (movie == null) return null;
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setTitleVi(movie.getTitleVi());
        dto.setDescription(movie.getDescription());
        dto.setDuration(movie.getDuration());
        dto.setLanguage(movie.getLanguage());
        dto.setGenre(movie.getGenre());
        dto.setReleaseDate(movie.getReleaseDate());
        dto.setPosterUrl(movie.getPosterUrl());
        dto.setBackdropUrl(movie.getBackdropUrl());
        dto.setTrailerUrl(movie.getTrailerUrl());
        dto.setDirector(movie.getDirector());
        dto.setActor(movie.getActor());
        dto.setRating(movie.getRating());
        dto.setCountry(movie.getCountry());
        dto.setIsActive(movie.getIsActive());
        dto.setCreatedAt(movie.getCreatedAt());
        dto.setUpdatedAt(movie.getUpdatedAt());
        dto.setShowtimes(movie.getShowtimes() != null ? movie.getShowtimes().stream().map(ShowtimeMapper::toDTO).collect(Collectors.toList()) : null);
        return dto;
    }

    public static Movie toEntity(MovieCreateDTO dto) {
        if (dto == null) return null;
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        movie.setTitleVi(dto.getTitleVi());
        movie.setDescription(dto.getDescription());
        movie.setDuration(dto.getDuration());
        movie.setLanguage(dto.getLanguage());
        movie.setGenre(dto.getGenre());
        movie.setReleaseDate(dto.getReleaseDate());
        movie.setPosterUrl(dto.getPosterUrl());
        movie.setBackdropUrl(dto.getBackdropUrl());
        movie.setTrailerUrl(dto.getTrailerUrl());
        movie.setDirector(dto.getDirector());
        movie.setActor(dto.getActor());
        movie.setRating(dto.getRating());
        movie.setCountry(dto.getCountry());
        movie.setIsActive(dto.getIsActive());
        return movie;
    }
} 