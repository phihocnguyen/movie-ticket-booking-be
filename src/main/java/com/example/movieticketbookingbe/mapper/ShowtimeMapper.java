package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.showtime.ShowtimeDTO;
import com.example.movieticketbookingbe.dto.showtime.ShowtimeCreateDTO;
import com.example.movieticketbookingbe.model.Showtime;
import com.example.movieticketbookingbe.dto.movie.MovieDTO;
import com.example.movieticketbookingbe.dto.screen.ScreenDTO;

public class ShowtimeMapper {
    public static ShowtimeDTO toDTO(Showtime showtime) {
        if (showtime == null) return null;
        ShowtimeDTO dto = new ShowtimeDTO();
        dto.setId(showtime.getId());
        dto.setMovie(showtime.getMovie() != null ? MovieMapper.toDTO(showtime.getMovie()) : null);
        dto.setScreen(showtime.getScreen() != null ? ScreenMapper.toDTO(showtime.getScreen()) : null);
        dto.setStartTime(showtime.getStartTime());
        dto.setEndTime(showtime.getEndTime());
        dto.setIsActive(showtime.getIsActive());
        dto.setCreatedAt(showtime.getCreatedAt());
        dto.setUpdatedAt(showtime.getUpdatedAt());
        return dto;
    }

    public static Showtime toEntity(ShowtimeCreateDTO dto) {
        if (dto == null) return null;
        Showtime showtime = new Showtime();
        showtime.setMovieId(dto.getMovieId());
        showtime.setTheaterId(dto.getTheaterId());
        showtime.setScreenId(dto.getScreenId());
        showtime.setStartTime(dto.getStartTime());
        showtime.setEndTime(dto.getEndTime());
        showtime.setPrice(dto.getPrice());
        showtime.setIsActive(dto.getIsActive());
        return showtime;
    }
} 