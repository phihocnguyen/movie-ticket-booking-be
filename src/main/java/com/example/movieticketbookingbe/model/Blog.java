package com.example.movieticketbookingbe.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String summary;

    private String thumbnail; // Lưu path hoặc URL file ảnh

    private boolean published;

    private String type;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
} 