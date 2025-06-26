package com.example.movieticketbookingbe.dto.blog;

import lombok.Data;

@Data
public class BlogPatchDTO {
    private String title;
    private String author;
    private String content;
    private String summary;
    private String thumbnail;
    private Boolean published;
    private String type;
} 