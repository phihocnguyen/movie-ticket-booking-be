package com.example.movieticketbookingbe.dto.movie;

import com.example.movieticketbookingbe.dto.showtime.ShowtimeDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MovieDTO {
    private Long id;
    private String title;
    private String titleVi;
    private String description;
    private Integer duration;
    private String language;
    private String genre;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private String posterUrl;
    private String backdropUrl;
    private String trailerUrl;
    private String director;
    private String actor;
    private Float rating;
    private String country;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ShowtimeDTO> showtimes;
} 