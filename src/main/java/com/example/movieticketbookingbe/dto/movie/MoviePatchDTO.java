package com.example.movieticketbookingbe.dto.movie;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MoviePatchDTO {
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
} 