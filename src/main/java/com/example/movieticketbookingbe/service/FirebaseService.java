package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.event.MovieCreatedEvent;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
public class FirebaseService {
    private FirebaseDatabase database;

   @PostConstruct
    public void init() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            InputStream serviceAccount = new ClassPathResource("movie-ticket-booking-47dbd-firebase-adminsdk-fbsvc-e76458cc0d.json").getInputStream();
            
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://movie-ticket-booking-47dbd-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .build();
            FirebaseApp.initializeApp(options);
        }
        database = FirebaseDatabase.getInstance();
    }

    public void pushNewMovieNotification(MovieCreatedEvent event) {
        Map<String, Object> notification = new HashMap<>();
        notification.put("type", "NEW_MOVIE");
        notification.put("message", "Phim Mới Cập Nhật !!!:";
        notification.put("movieId", event.getMovieId());
        notification.put("createdAt", new Date().getTime());
        notification.put("readUsers", new ArrayList<>());
        System.out.println("[FirebaseService] Pushing notification to Firebase: " + notification);
        DatabaseReference ref = database.getReference("notifications");
        ref.push().setValueAsync(notification).addListener(
            () -> System.out.println("[FirebaseService] Push to Firebase completed!"),
            Runnable::run
        );
    }
} 