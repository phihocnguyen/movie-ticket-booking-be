package com.example.movieticketbookingbe.repository;

import com.example.movieticketbookingbe.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
} 