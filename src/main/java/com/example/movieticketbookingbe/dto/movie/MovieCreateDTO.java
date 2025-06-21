package com.example.movieticketbookingbe.dto.movie;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MovieCreateDTO {
    private String title;
    private String titleVi;
    private String description;
    private Integer duration;
    private String language;
    private String genre;
    private LocalDate releaseDate;
    private String posterUrl;
    private String backdropUrl;
    private String trailerUrl;
    private String director;
    private String actor;
    private Float rating;
    private String country;
    private Boolean isActive;
} 