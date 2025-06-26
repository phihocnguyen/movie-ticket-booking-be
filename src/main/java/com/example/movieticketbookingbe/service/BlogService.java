package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.model.Blog;
import java.util.List;
import java.util.Optional;

public interface BlogService {
    Blog createBlog(Blog blog);
    Blog updateBlog(Long id, Blog blog);
    void deleteBlog(Long id);
    Optional<Blog> getBlogById(Long id);
    List<Blog> getAllBlogs();
} 