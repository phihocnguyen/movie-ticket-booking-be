package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.dto.DashBoard.*;
import com.example.movieticketbookingbe.model.Booking;
import com.example.movieticketbookingbe.repository.BookingRepository;
import com.example.movieticketbookingbe.repository.MovieRepository;
import com.example.movieticketbookingbe.repository.ShowtimeRepository;
import com.example.movieticketbookingbe.repository.TheaterRepository;
import com.example.movieticketbookingbe.service.DashboardService;
import com.example.movieticketbookingbe.service.SystemSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class DashBoardServiceImpl implements DashboardService {
    private final BookingRepository bookingRepository;
    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;
    private final SystemSettingService systemSettingService;
    private final ShowtimeRepository showtimeRepository;

    @Override
    public DashboardOverviewDTO getOverview() {
        long totalRevenue = bookingRepository.sumTotalRevenue();

        // Lấy commissionRate (BigDecimal)
        BigDecimal commissionRate = systemSettingService.getGlobalSetting().getCommissionRate();

        // Tính doanh thu hệ thống (totalRevenue * commissionRate)

        BigDecimal netRevenueDecimal = BigDecimal.valueOf(totalRevenue)
                .multiply(commissionRate.divide(BigDecimal.valueOf(100)));
        long netRevenue = netRevenueDecimal.longValue();

        long totalTicketsSold = bookingRepository.countTicketsSold();
        long totalMovies = movieRepository.countByIsActiveTrue();
        long totalTheaters = theaterRepository.countByIsActiveTrue();

        return new DashboardOverviewDTO(netRevenue, totalTicketsSold, totalMovies, totalTheaters);
    }


    public List<RevenueChartDTO> getRevenueChart(String from, String to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        YearMonth toMonth;
        YearMonth fromMonth;

        if (to == null || to.isBlank()) {
            toMonth = YearMonth.now();
        } else {
            toMonth = YearMonth.parse(to.trim(), formatter);

            if (toMonth.isAfter(YearMonth.now())) {
                throw new IllegalArgumentException("Tháng kết thúc không được lớn hơn tháng hiện tại.");
            }
        }

        if (from == null || from.isBlank()) {
            fromMonth = toMonth.minusMonths(5); // lấy 6 tháng bao gồm cả toMonth
        } else {
            fromMonth = YearMonth.parse(from.trim(), formatter);
        }
        if (fromMonth.isAfter(toMonth)) {
            throw new IllegalArgumentException("Tháng bắt đầu không được lớn hơn tháng kết thúc.");
        }
        long monthsBetween = ChronoUnit.MONTHS.between(fromMonth, toMonth) + 1;
        if (monthsBetween > 6) {
            throw new IllegalArgumentException("Chỉ được lọc tối đa 6 tháng kể cả tháng hiện tại.");
        }

        List<RevenueChartDTO> result = new ArrayList<>();
        BigDecimal commissionRate = systemSettingService.getGlobalSetting().getCommissionRate();

        for (int i = 0; i < monthsBetween; i++) {
            YearMonth current = fromMonth.plusMonths(i);
            LocalDateTime startDate = current.atDay(1).atStartOfDay();
            LocalDateTime endDate = current.atEndOfMonth().atTime(LocalTime.MAX);

            Double revenue = bookingRepository.sumRevenueByDateRange(startDate, endDate);

            long netRevenue = 0;
            if (revenue != null) {
                BigDecimal netRevenueDecimal = BigDecimal.valueOf(revenue)
                        .multiply(commissionRate.divide(BigDecimal.valueOf(100)));
                netRevenue = netRevenueDecimal.longValue();
            }

            result.add(new RevenueChartDTO(current.toString(), (double) netRevenue));
        }

        return result;
    }

    public List<TopTheaterDTO> getTop5TheatersByRevenue() {
        BigDecimal commissionRate = systemSettingService.getGlobalSetting().getCommissionRate()
                .divide(BigDecimal.valueOf(100)); // 10% → 0.1

        List<TopTheaterDTO> topGrossList = bookingRepository.findTopTheatersByRevenue(PageRequest.of(0, 5));

        // Chuyển gross → net
        return topGrossList.stream().map(dto -> {
            double gross = dto.getRevenue();
            double net = gross * (1 - commissionRate.doubleValue());
            return new TopTheaterDTO(dto.getTheaterName(), net);
        }).collect(Collectors.toList());
    }

    public OwnerDashboardOverviewDTO getOwnerOverview(Long ownerId) {
        Double grossRevenue = bookingRepository.getTotalRevenueByOwner(ownerId);
        BigDecimal commissionRate = systemSettingService.getGlobalSetting().getCommissionRate()
                .divide(BigDecimal.valueOf(100)); // 10% → 0.1

        double netRevenue = grossRevenue != null ? grossRevenue * (1 - commissionRate.doubleValue()) : 0.0;

        long totalTicketsSold = Optional.ofNullable(bookingRepository.getTotalTicketsSoldByOwner(ownerId)).orElse(0L);
        long totalShowtimes = Optional.ofNullable(showtimeRepository.getTotalShowtimesByOwner(ownerId)).orElse(0L);
        long totalTheaters = Optional.ofNullable(theaterRepository.getTotalTheatersByOwner(ownerId)).orElse(0L);

        return new OwnerDashboardOverviewDTO(netRevenue, totalTicketsSold, totalShowtimes, totalTheaters);
    }

    public List<RevenueChartDTO> getRevenueChartByOwner(String from, String to, Long ownerId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        YearMonth toMonth = (to == null || to.isBlank()) ? YearMonth.now() : YearMonth.parse(to.trim(), formatter);
        YearMonth fromMonth = (from == null || from.isBlank()) ? toMonth.minusMonths(5) : YearMonth.parse(from.trim(), formatter);
        YearMonth currentMonth = YearMonth.now();
        if (toMonth.isAfter(currentMonth)) {
            throw new IllegalArgumentException("Tháng kết thúc không được vượt quá tháng hiện tại.");
        }
        if (fromMonth.isAfter(toMonth)) {
            throw new IllegalArgumentException("Tháng bắt đầu không được lớn hơn tháng kết thúc.");
        }

        long monthsBetween = ChronoUnit.MONTHS.between(fromMonth, toMonth) + 1;
        if (monthsBetween > 6) {
            throw new IllegalArgumentException("Chỉ cho phép lọc tối đa 6 tháng.");
        }

        List<RevenueChartDTO> result = new ArrayList<>();
        BigDecimal commissionRate = systemSettingService.getGlobalSetting().getCommissionRate()
                .divide(BigDecimal.valueOf(100)); // ví dụ 10% => 0.1

        for (int i = 0; i < monthsBetween; i++) {
            YearMonth current = fromMonth.plusMonths(i);
            LocalDateTime startDate = current.atDay(1).atStartOfDay();
            LocalDateTime endDate = current.atEndOfMonth().atTime(LocalTime.MAX);

            Double grossRevenue = bookingRepository.sumRevenueByDateRangeAndOwner(startDate, endDate, ownerId);

            double netRevenue = 0.0;
            if (grossRevenue != null) {
                netRevenue = grossRevenue * (1 - commissionRate.doubleValue());
            }

            String label = current.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            result.add(new RevenueChartDTO(label, netRevenue));
        }

        return result;
    }

    public List<TopFoodDTO> getTop3FoodsByOwner(Long ownerId) {
        List<Booking> bookings = bookingRepository.findCompletedBookingsByOwner(ownerId);

        Map<String, Long> foodCountMap = new HashMap<>();

        for (Booking booking : bookings) {
            for (Booking.BookingFoodInfo foodInfo : booking.getBookingFoods()) {
                String foodName = foodInfo.getFoodName() != null ? foodInfo.getFoodName() : "Unknown";
                Long quantity = foodCountMap.getOrDefault(foodName, 0L);
                foodCountMap.put(foodName, quantity + foodInfo.getQuantity());
            }
        }

        return foodCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .map(e -> new TopFoodDTO(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }


}
