package com.example.movieticketbookingbe.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SeatBookingEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String SEAT_BOOKING_TOPIC = "seat-booking-events";

    public void publishEvent(SeatBookingEvent event) {
        event.setTimestamp(LocalDateTime.now());
        kafkaTemplate.send(SEAT_BOOKING_TOPIC, event);
    }
}