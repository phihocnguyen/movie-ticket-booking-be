package com.example.movieticketbookingbe.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieCreatedEvent {
    private Long movieId;
    private String title;
} 