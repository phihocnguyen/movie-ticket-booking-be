package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import com.example.movieticketbookingbe.utils.CloudinaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {
    private final CloudinaryService cloudinaryService;

    @Operation(summary = "Upload file to Cloudinary", description = "Uploads a file and returns its URL")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "File uploaded successfully", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "400", description = "Invalid file")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDTO<String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            var uploadResult = cloudinaryService.upload(file.getBytes(), new HashMap<>());
            String url = uploadResult.get("secure_url").toString();
            return ResponseEntity.ok(new ApiResponseDTO<>(200, "File uploaded successfully", url));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseDTO<>(400, "Upload failed: " + e.getMessage(), null));
        }
    }
} 