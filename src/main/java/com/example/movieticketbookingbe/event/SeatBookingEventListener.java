package com.example.movieticketbookingbe.event;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.movieticketbookingbe.service.SeatLockService;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

@Slf4j
@Component
@RequiredArgsConstructor
public class SeatBookingEventListener implements MessageListener {
    private final ObjectMapper objectMapper;
    private final SeatLockService seatLockService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            SeatBookingEvent event = objectMapper.readValue(message.getBody(), SeatBookingEvent.class);
            log.info("Received seat booking event: {}", event);

            switch (event.getEventType()) {
                case "LOCK":
                    handleLockEvent(event);
                    break;
                case "UNLOCK":
                    handleUnlockEvent(event);
                    break;
                case "BOOK":
                    handleBookEvent(event);
                    break;
                default:
                    log.warn("Unknown event type: {}", event.getEventType());
            }
        } catch (Exception e) {
            log.error("Error processing seat booking event", e);
        }
    }

    private void handleLockEvent(SeatBookingEvent event) {
        try {
            boolean locked = seatLockService.lockSeat(event.getSeatId(), event.getUserId());
            if (locked) {
                log.info("Successfully locked seat {} for user {}", event.getSeatNumber(), event.getUserId());
            } else {
                log.warn("Failed to lock seat {} for user {}", event.getSeatNumber(), event.getUserId());
            }
        } catch (Exception e) {
            log.error("Error handling lock event for seat {}", event.getSeatNumber(), e);
        }
    }

    private void handleUnlockEvent(SeatBookingEvent event) {
        try {
            boolean unlocked = seatLockService.unlockSeat(event.getSeatId(), event.getUserId());
            if (unlocked) {
                log.info("Successfully unlocked seat {} for user {}", event.getSeatNumber(), event.getUserId());
            } else {
                log.warn("Failed to unlock seat {} for user {}", event.getSeatNumber(), event.getUserId());
            }
        } catch (Exception e) {
            log.error("Error handling unlock event for seat {}", event.getSeatNumber(), e);
        }
    }

    private void handleBookEvent(SeatBookingEvent event) {
        try {
            // Verify the seat is still locked by this user before booking
            Long lockOwner = seatLockService.getLockOwner(event.getSeatId());
            if (lockOwner != null && lockOwner.equals(event.getUserId())) {
                // Proceed with booking
                log.info("Processing booking for seat {} by user {}", event.getSeatNumber(), event.getUserId());
                // TODO: Add actual booking logic here
            } else {
                log.warn("Cannot book seat {} - lock not held by user {}", event.getSeatNumber(), event.getUserId());
            }
        } catch (Exception e) {
            log.error("Error handling book event for seat {}", event.getSeatNumber(), e);
        }
    }
}