package com.example.movieticketbookingbe.dto.blog;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class BlogDTO {
    private Long id;
    private String title;
    private String author;
    private String content;
    private String summary;
    private String thumbnail;
    private boolean published;
    private String type;
    private LocalDateTime postDay;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
