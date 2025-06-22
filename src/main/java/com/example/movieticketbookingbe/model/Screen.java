package com.example.movieticketbookingbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "screens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String screenName;

    @Column(nullable = false)
    private String screenType;

    @Column(nullable = false)
    private Integer totalSeats;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(name = "theater_id", nullable = false)
    private Long theaterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id", insertable = false, updatable = false)
    @JsonIgnore
    private Theater theater;

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Showtime> showtimes = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void setCapacity(Integer capacity) {
        this.totalSeats = capacity;
    }
}