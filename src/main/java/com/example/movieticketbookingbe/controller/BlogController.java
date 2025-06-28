package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import com.example.movieticketbookingbe.dto.blog.BlogCreateDTO;
import com.example.movieticketbookingbe.dto.blog.BlogDTO;
import com.example.movieticketbookingbe.dto.voucher.VoucherCreateDTO;
import com.example.movieticketbookingbe.dto.voucher.VoucherDTO;
import com.example.movieticketbookingbe.dto.voucher.VoucherPatchDTO;
import com.example.movieticketbookingbe.mapper.BlogMapper;
import com.example.movieticketbookingbe.mapper.VoucherMapper;
import com.example.movieticketbookingbe.model.Blog;
import com.example.movieticketbookingbe.model.Voucher;
import com.example.movieticketbookingbe.service.BlogService;
import com.example.movieticketbookingbe.dto.blog.BlogPatchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDTO<BlogDTO>> createVoucher(@RequestBody BlogCreateDTO blogCreateDTO) {
        Blog blog = BlogMapper.toEntity(blogCreateDTO);
        BlogDTO dto = BlogMapper.toResponseDTO(blogService.createBlog(blog));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Created", dto));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponseDTO<BlogDTO>> updateVoucher(@PathVariable Long id, @RequestBody BlogPatchDTO patchDTO) {
        BlogDTO dto = BlogMapper.toResponseDTO(blogService.patchBlog(id, patchDTO));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Updated voucher successfully", dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Deleted", null));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponseDTO<BlogDTO>> getBlogById(@PathVariable Long id) {
        return blogService.getBlogById(id)
                .map(blog -> {
                    BlogDTO dto = BlogMapper.toResponseDTO(blog);
                    return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy blog thành công", dto));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponseDTO<>(404, "Không tìm thấy blog", null)));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponseDTO<List<BlogDTO>>> getAllBlogs() {
        List<BlogDTO> dtos = blogService.getAllBlogs().stream().map(BlogMapper::toResponseDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy danh sách blog thành công",dtos ));
    }
} 