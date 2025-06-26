package com.example.movieticketbookingbe.dto.blog;

import lombok.Data;

@Data
public class BlogCreateDTO {
    private String title;
    private String author;
    private String content;
    private String summary;
    private String thumbnail;
    private boolean published;
    private String type;
} 