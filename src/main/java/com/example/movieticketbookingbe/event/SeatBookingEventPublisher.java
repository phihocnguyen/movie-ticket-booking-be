package com.example.movieticketbookingbe.event;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SeatBookingEventPublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String SEAT_BOOKING_CHANNEL = "seat:booking:events";

    public void publishEvent(SeatBookingEvent event) {
        event.setTimestamp(LocalDateTime.now());
        redisTemplate.convertAndSend(SEAT_BOOKING_CHANNEL, event);
    }
}