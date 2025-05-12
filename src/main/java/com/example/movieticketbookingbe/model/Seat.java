package com.example.movieticketbookingbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@Entity
@Table(name = "seats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    @JsonIgnore
    private Screen screen;

    @Column(nullable = false)
    private String seatNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_type_id", nullable = false)
    @JsonIgnore
    private SeatType seatType;

    @Column(name = "seat_type_id", insertable = false, updatable = false)
    private Long seatTypeId;

    @Column(nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "seat")
    @JsonIgnore
    private List<BookingSeat> bookingSeats;

    @Transient
    @JsonInclude
    public String getSeatTypeName() {
        return seatType != null ? seatType.getName() : null;
    }

    @Transient
    @JsonInclude
    public Double getPriceMultiplier() {
        return seatType != null ? seatType.getPriceMultiplier() : null;
    }
}