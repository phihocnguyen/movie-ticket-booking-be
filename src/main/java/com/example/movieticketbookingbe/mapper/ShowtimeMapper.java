package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.ShowtimeDTO;
import com.example.movieticketbookingbe.model.Showtime;

public class ShowtimeMapper {
    public static ShowtimeDTO toDTO(Showtime showtime) {
        if (showtime == null) return null;
        ShowtimeDTO dto = new ShowtimeDTO();
        dto.setId(showtime.getId());
        dto.setMovieId(showtime.getMovieId());
        dto.setTheaterId(showtime.getTheaterId());
        dto.setScreenId(showtime.getScreenId());
        dto.setStartTime(showtime.getStartTime());
        dto.setEndTime(showtime.getEndTime());
        dto.setPrice(showtime.getPrice());
        dto.setIsActive(showtime.getIsActive());
        dto.setMovieTitle(showtime.getMovieTitle());
        dto.setTheaterName(showtime.getTheaterName());
        dto.setScreenName(showtime.getScreenName());
        dto.setTheaterAddress(showtime.getTheaterAddress());
        return dto;
    }
} 