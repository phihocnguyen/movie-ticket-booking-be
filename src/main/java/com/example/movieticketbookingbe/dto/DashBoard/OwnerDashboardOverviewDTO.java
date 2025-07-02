package com.example.movieticketbookingbe.dto.DashBoard;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OwnerDashboardOverviewDTO {
    private double totalRevenue;
    private long totalTicketsSold;
    private long totalShowtimes;
    private long totalTheaters;

    public OwnerDashboardOverviewDTO(double totalRevenue, long totalTicketsSold, long totalShowtimes, long totalTheaters) {
        this.totalRevenue = totalRevenue;
        this.totalTicketsSold = totalTicketsSold;
        this.totalShowtimes = totalShowtimes;
        this.totalTheaters = totalTheaters;
    }
}
