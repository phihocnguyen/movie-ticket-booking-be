package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.MovieDTO;
import com.example.movieticketbookingbe.model.Movie;

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
        return dto;
    }
} 