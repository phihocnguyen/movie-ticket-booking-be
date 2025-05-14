package com.example.movieticketbookingbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "showtime_id", nullable = false)
    private Long showtimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showtime_id", insertable = false, updatable = false)
    private Showtime showtime;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime bookingTime;

    @Column(nullable = false)
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(nullable = false)
    private Boolean isActive = true;

    @ElementCollection
    @CollectionTable(name = "booking_seats", joinColumns = @JoinColumn(name = "booking_id"))
    private List<BookingSeatInfo> bookingSeats = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "booking_foods", joinColumns = @JoinColumn(name = "booking_id"))
    private List<BookingFoodInfo> bookingFoods = new ArrayList<>();

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    @JsonIgnore
    private Payment payment;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingSeatInfo {
        @Column(name = "seat_id", nullable = false)
        private Long seatId;

        @Column(nullable = false)
        private Double price;

        @Transient
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String seatName;

        @Transient
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String seatType;

        @Transient
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer rowNumber;

        @Transient
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer columnNumber;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingFoodInfo {
        @Column(name = "food_inventory_id", nullable = false)
        private Long foodInventoryId;

        @Column(nullable = false)
        private Integer quantity;

        @Column(nullable = false)
        private Double price;

        @Transient
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String foodName;
    }

    public enum BookingStatus {
        PENDING,
        CONFIRMED,
        CANCELLED,
        COMPLETED
    }

    @JsonProperty("showtime")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Showtime getShowtime() {
        return showtime;
    }
}