package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.Blog;
import com.example.movieticketbookingbe.repository.BlogRepository;
import com.example.movieticketbookingbe.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.movieticketbookingbe.dto.blog.BlogCreateDTO;
import com.example.movieticketbookingbe.dto.blog.BlogPatchDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;

    @Override
    public Blog createBlog(BlogCreateDTO dto) {
        Blog blog = Blog.builder()
            .title(dto.getTitle())
            .author(dto.getAuthor())
            .content(dto.getContent())
            .summary(dto.getSummary())
            .thumbnail(dto.getThumbnail())
            .published(dto.isPublished())
            .type(dto.getType())
            .createdAt(java.time.LocalDateTime.now())
            .updatedAt(java.time.LocalDateTime.now())
            .build();
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Optional<Blog> existing = blogRepository.findById(id);
        if (existing.isPresent()) {
            Blog b = existing.get();
            b.setTitle(blog.getTitle());
            b.setAuthor(blog.getAuthor());
            b.setContent(blog.getContent());
            b.setSummary(blog.getSummary());
            b.setThumbnail(blog.getThumbnail());
            b.setPublished(blog.isPublished());
            b.setType(blog.getType());
            b.setUpdatedAt(LocalDateTime.now());
            return blogRepository.save(b);
        }
        throw new RuntimeException("Blog not found");
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Blog patchBlog(Long id, BlogPatchDTO dto) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
        if (dto.getTitle() != null) blog.setTitle(dto.getTitle());
        if (dto.getAuthor() != null) blog.setAuthor(dto.getAuthor());
        if (dto.getContent() != null) blog.setContent(dto.getContent());
        if (dto.getSummary() != null) blog.setSummary(dto.getSummary());
        if (dto.getThumbnail() != null) blog.setThumbnail(dto.getThumbnail());
        if (dto.getPublished() != null) blog.setPublished(dto.getPublished());
        if (dto.getType() != null) blog.setType(dto.getType());
        blog.setUpdatedAt(java.time.LocalDateTime.now());
        return blogRepository.save(blog);
    }

    @Override
    public Blog createBlog(Blog blog) {
        blog.setCreatedAt(java.time.LocalDateTime.now());
        blog.setUpdatedAt(java.time.LocalDateTime.now());
        return blogRepository.save(blog);
    }
} 