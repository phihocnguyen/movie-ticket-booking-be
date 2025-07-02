package com.example.movieticketbookingbe.dto.DashBoard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TopTheaterDTO {
    private String theaterName;
    private double revenue;

    public TopTheaterDTO(String theaterName, double revenue) {
        this.theaterName = theaterName;
        this.revenue = revenue;
    }
}
