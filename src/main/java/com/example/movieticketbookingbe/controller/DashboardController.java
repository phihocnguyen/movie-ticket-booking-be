package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import com.example.movieticketbookingbe.dto.DashBoard.*;
import com.example.movieticketbookingbe.service.DashboardService;
import com.example.movieticketbookingbe.service.impl.DashBoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController  {
    private final DashboardService dashboardService;
    @GetMapping("/overview")
    public ResponseEntity<DashboardOverviewDTO> getOverview() {
        DashboardOverviewDTO overview = dashboardService.getOverview();
        return ResponseEntity.ok(overview);
    }

    @GetMapping("/revenue-chart")
    public ResponseEntity<ApiResponseDTO<List<RevenueChartDTO>>> getRevenueChart(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to) {
        try {
            List<RevenueChartDTO> data = dashboardService.getRevenueChart(from, to);
            return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy doanh thu theo tháng thành công", data));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponseDTO<>(400, e.getMessage(), null)
            );
        }
    }


    @GetMapping("/top-theaters")
    public ResponseEntity<ApiResponseDTO<List<TopTheaterDTO>>> getTopTheaters() {
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy top 5 rạp doanh thu cao nhất thành công",dashboardService.getTop5TheatersByRevenue()));
    }
    @GetMapping("/owner-overview/{ownerId}")
    public ResponseEntity<ApiResponseDTO<OwnerDashboardOverviewDTO>> getOwnerOverview(
            @PathVariable Long ownerId) {
        OwnerDashboardOverviewDTO dto = dashboardService.getOwnerOverview(ownerId);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy dữ liệu tổng quan cho owner thành công", dto));
    }

    @GetMapping("/revenue-chart/owner/{ownerId}")
    public ResponseEntity<ApiResponseDTO<List<RevenueChartDTO>>> getRevenueChartByOwner(
            @PathVariable Long ownerId,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to) {
        try {
            List<RevenueChartDTO> chart = dashboardService.getRevenueChartByOwner(from, to, ownerId);
            return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy biểu đồ doanh thu theo tháng thành công", chart));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponseDTO<>(400, e.getMessage(), null)
            );
        }
    }
    @GetMapping("/top-foods/owner/{ownerId}")
    public ResponseEntity<ApiResponseDTO<List<TopFoodDTO>>> getTopFoodsByOwner(@PathVariable Long ownerId) {
        List<TopFoodDTO> topFoods = dashboardService.getTop3FoodsByOwner(ownerId);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy top 3 món ăn thành công", topFoods));
    }
}
