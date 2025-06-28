package com.example.movieticketbookingbe.mapper;

import com.example.movieticketbookingbe.dto.blog.BlogCreateDTO;
import com.example.movieticketbookingbe.dto.blog.BlogDTO;
import com.example.movieticketbookingbe.dto.blog.BlogPatchDTO;
import com.example.movieticketbookingbe.model.Blog;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BlogMapper {

    // Convert Entity -> Response DTO
    public static BlogDTO toResponseDTO(Blog blog) {
        BlogDTO dto = new BlogDTO();
        dto.setId(blog.getId());
        dto.setTitle(blog.getTitle());
        dto.setAuthor(blog.getAuthor());
        dto.setContent(blog.getContent());
        dto.setSummary(blog.getSummary());
        dto.setThumbnail(blog.getThumbnail());
        dto.setPublished(blog.isPublished());
        dto.setType(blog.getType());
        dto.setCreatedAt(blog.getCreatedAt());
        dto.setUpdatedAt(blog.getUpdatedAt());
        dto.setPostDay(blog.getPostDay());
        return dto;
    }

    // Convert CreateDTO -> Entity
    public static Blog toEntity(BlogCreateDTO dto) {
        Blog blog = new Blog();
        blog.setTitle(dto.getTitle());
        blog.setAuthor(dto.getAuthor());
        blog.setContent(dto.getContent());
        blog.setSummary(dto.getSummary());
        blog.setThumbnail(dto.getThumbnail());
        blog.setPublished(dto.isPublished());
        blog.setType(dto.getType());
        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());

        if (dto.isPublished()) {
            blog.setPostDay(LocalDateTime.now());
        }

        return blog;
    }

    // Update existing entity from PatchDTO
    public static void updateEntityFromDTO(Blog blog, BlogPatchDTO dto) {
        if (dto.getTitle() != null) blog.setTitle(dto.getTitle());
        if (dto.getAuthor() != null) blog.setAuthor(dto.getAuthor());
        if (dto.getContent() != null) blog.setContent(dto.getContent());
        if (dto.getSummary() != null) blog.setSummary(dto.getSummary());
        if (dto.getThumbnail() != null) blog.setThumbnail(dto.getThumbnail());
        if (dto.getType() != null) blog.setType(dto.getType());

        // Nếu trước đó chưa được public mà giờ bật published => set ngày đăng
        if (!blog.isPublished() && Boolean.TRUE.equals(dto.getPublished())) {
            blog.setPostDay(LocalDateTime.now());
        }

        if (dto.getPublished() != null) blog.setPublished(dto.getPublished());

        blog.setUpdatedAt(LocalDateTime.now());
    }

}
