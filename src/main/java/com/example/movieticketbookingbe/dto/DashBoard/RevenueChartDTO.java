package com.example.movieticketbookingbe.dto.DashBoard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueChartDTO {
    private String month; // yyyy-MM
    private Double revenue;
}
