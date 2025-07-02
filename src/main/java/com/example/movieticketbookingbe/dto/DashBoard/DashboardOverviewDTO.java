package com.example.movieticketbookingbe.dto.DashBoard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardOverviewDTO {
    private long totalRevenue;
    private long totalTicketsSold;
    private long totalMovies;
    private long totalTheaters;
}
