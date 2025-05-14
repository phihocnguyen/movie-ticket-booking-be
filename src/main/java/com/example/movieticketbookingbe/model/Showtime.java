package com.example.movieticketbookingbe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "showtimes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIgnore
    private Movie movie;

    @Column(name = "movie_id", insertable = false, updatable = false)
    private Long movieId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id", nullable = false)
    @JsonIgnore
    private Theater theater;

    @Column(name = "theater_id", insertable = false, updatable = false)
    private Long theaterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    @JsonIgnore
    private Screen screen;

    @Column(name = "screen_id", insertable = false, updatable = false)
    private Long screenId;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Boolean isActive = true;

    @OneToMany
    @JoinColumn(name = "showtime_id")
    @JsonIgnore
    private List<Booking> bookings;

    @Transient
    @JsonInclude
    public String getMovieTitle() {
        return movie != null ? movie.getTitle() : null;
    }

    @Transient
    @JsonInclude
    public String getTheaterName() {
        return theater != null ? theater.getName() : null;
    }

    @Transient
    @JsonInclude
    public String getScreenName() {
        return screen != null ? screen.getScreenName() : null;
    }

    @Transient
    @JsonInclude
    public String getTheaterAddress() {
        return theater != null ? theater.getAddress() : null;
    }
}