package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.dto.DashBoard.*;

import java.util.List;

public interface DashboardService {
    DashboardOverviewDTO getOverview();
    List<RevenueChartDTO> getRevenueChart(String from, String to);
    List<TopTheaterDTO> getTop5TheatersByRevenue();
    OwnerDashboardOverviewDTO getOwnerOverview(Long ownerId);
    List<RevenueChartDTO> getRevenueChartByOwner(String from, String to, Long ownerId);
    List<TopFoodDTO> getTop3FoodsByOwner(Long ownerId);
}
