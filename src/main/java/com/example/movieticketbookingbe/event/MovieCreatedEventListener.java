package com.example.movieticketbookingbe.event;

import com.example.movieticketbookingbe.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.PostConstruct;

@Slf4j
@Component
public class MovieCreatedEventListener {
    
    @Autowired
    private FirebaseService firebaseService;

    @PostConstruct
    public void init() {
        log.info("[MovieCreatedEventListener] Listener initialized and ready to receive events");
    }

    @KafkaListener(topics = "movie-created-v2", groupId = "notification-group-test", containerFactory = "movieCreatedEventKafkaListenerContainerFactory")
    public void handleMovieCreated(MovieCreatedEvent event) {
        log.info("=== MOVIE CREATED EVENT LISTENER CALLED ===");
        log.info("Received MovieCreatedEvent: movieId={}, title={}", event.getMovieId(), event.getTitle());
        
        try {
            log.info("Processing movie created event for movie: {}", event.getTitle());
            
            // Push notification to Firebase
            firebaseService.pushNewMovieNotification(event);
            
            log.info("Successfully processed movie created event and pushed notification to Firebase");
            
        } catch (Exception e) {
            log.error("Error processing movie created event: {}", e.getMessage(), e);
        }
    }
} 