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
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        if (dto.getTitle() != null) blog.setTitle(dto.getTitle());
        if (dto.getAuthor() != null) blog.setAuthor(dto.getAuthor());
        if (dto.getContent() != null) blog.setContent(dto.getContent());
        if (dto.getSummary() != null) blog.setSummary(dto.getSummary());
        if (dto.getThumbnail() != null) blog.setThumbnail(dto.getThumbnail());
        if (dto.getType() != null) blog.setType(dto.getType());

        // Nếu đang chưa được publish mà người dùng cập nhật thành publish thì gán ngày đăng
        if (!blog.isPublished() && Boolean.TRUE.equals(dto.getPublished())) {
            blog.setPostDay(LocalDateTime.now());
        }

        if (dto.getPublished() != null) blog.setPublished(dto.getPublished());

        blog.setUpdatedAt(LocalDateTime.now());
        return blogRepository.save(blog);
    }


    @Override
    public Blog createBlog(Blog blog) {
        LocalDateTime now = LocalDateTime.now();
        blog.setCreatedAt(now);
        blog.setUpdatedAt(now);
        if (blog.isPublished() && blog.getPostDay() == null) {
            blog.setPostDay(now);
        }
        return blogRepository.save(blog);
    }
} 