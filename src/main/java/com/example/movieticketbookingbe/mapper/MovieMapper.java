package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.movie.MovieDTO;
import com.example.movieticketbookingbe.model.Movie;
import com.example.movieticketbookingbe.dto.movie.MovieCreateDTO;

public class MovieMapper {
    public static MovieDTO toDTO(Movie movie) {
        if (movie == null) return null;
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setTitleVi(movie.getTitleVi());
        dto.setDescription(movie.getDescription());
        dto.setDirector(movie.getDirector());
        dto.setActor(movie.getActor());
        dto.setReleaseDate(movie.getReleaseDate());
        dto.setDuration(movie.getDuration());
        dto.setGenre(movie.getGenre());
        dto.setLanguage(movie.getLanguage());
        dto.setCountry(movie.getCountry());
        dto.setPosterUrl(movie.getPosterUrl());
        dto.setBackdropUrl(movie.getBackdropUrl());
        dto.setTrailerUrl(movie.getTrailerUrl());
        dto.setRating(movie.getRating());
        dto.setIsActive(movie.getIsActive());
        dto.setCreatedAt(movie.getCreatedAt());
        dto.setUpdatedAt(movie.getUpdatedAt());
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