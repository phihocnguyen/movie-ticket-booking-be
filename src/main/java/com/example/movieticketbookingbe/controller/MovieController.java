package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.Movie;
import com.example.movieticketbookingbe.service.MovieService;
import com.example.movieticketbookingbe.dto.MovieDTO;
import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import com.example.movieticketbookingbe.mapper.MovieMapper;
import com.example.movieticketbookingbe.dto.movie.MovieCreateDTO;
import com.example.movieticketbookingbe.dto.movie.MoviePatchDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
@Tag(name = "Movie", description = "Movie management APIs")
public class MovieController {
    private final MovieService movieService;

    @Operation(summary = "Create a new movie", description = "Creates a new movie with the provided information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movie created successfully", content = @Content(schema = @Schema(implementation = Movie.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDTO<MovieDTO>> createMovie(@RequestBody MovieCreateDTO movieCreateDTO) {
        Movie movie = MovieMapper.toEntity(movieCreateDTO);
        MovieDTO dto = MovieMapper.toDTO(movieService.createMovie(movie));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Movie created successfully", dto));
    }

    @Operation(summary = "Update a movie", description = "Updates an existing movie by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movie updated successfully"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<MovieDTO>> patchMovie(
            @Parameter(description = "ID of the movie to update") @PathVariable Long id,
            @RequestBody MoviePatchDTO patchDTO) {
        MovieDTO dto = MovieMapper.toDTO(movieService.patchMovie(id, patchDTO));
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Movie updated successfully", dto));
    }

    @Operation(summary = "Delete a movie", description = "Deletes a movie by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movie deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteMovie(
            @Parameter(description = "ID of the movie to delete") @PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Xóa phim thành công", null));
    }

    @Operation(summary = "Get a movie by ID", description = "Returns a movie by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movie found", content = @Content(schema = @Schema(implementation = Movie.class))),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<MovieDTO>> getMovieById(
            @Parameter(description = "ID of the movie to retrieve") @PathVariable Long id) {
        return movieService.getMovieById(id)
                .map(movie -> ResponseEntity.ok(new ApiResponseDTO<>(200, "Tìm thấy phim", MovieMapper.toDTO(movie))))
                .orElse(ResponseEntity.ok(new ApiResponseDTO<>(404, "Không tìm thấy phim", null)));
    }

    @Operation(summary = "Get all movies", description = "Returns a list of all movies")
    @ApiResponse(responseCode = "200", description = "List of movies retrieved successfully", content = @Content(schema = @Schema(implementation = Movie.class)))
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<MovieDTO>>> getAllMovies() {
        List<MovieDTO> dtos = movieService.getAllMovies().stream().map(MovieMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy danh sách phim thành công", dtos));
    }

    @Operation(summary = "Search movies", description = "Search movies by title, genre, and language")
    @ApiResponse(responseCode = "200", description = "Search results retrieved successfully")
    @GetMapping("/search")
    public ResponseEntity<ApiResponseDTO<List<MovieDTO>>> searchMovies(
            @Parameter(description = "Movie title to search for") @RequestParam(required = false) String title,
            @Parameter(description = "Movie genre to filter by") @RequestParam(required = false) String genre,
            @Parameter(description = "Movie language to filter by") @RequestParam(required = false) String language) {
        List<MovieDTO> dtos = movieService.searchMovies(title, genre, language).stream().map(MovieMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Tìm kiếm phim thành công", dtos));
    }

    @Operation(summary = "Get random movies", description = "Returns 3 random movies")
    @ApiResponse(responseCode = "200", description = "Random movies retrieved successfully")
    @GetMapping("/random")
    public ResponseEntity<ApiResponseDTO<List<MovieDTO>>> getRandomMovies() {
        List<MovieDTO> dtos = movieService.getRandomMovies().stream().map(MovieMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy phim ngẫu nhiên thành công", dtos));
    }

    @Operation(summary = "Get latest movies", description = "Returns 10 latest movies")
    @ApiResponse(responseCode = "200", description = "Latest movies retrieved successfully")
    @GetMapping("/latest")
    public ResponseEntity<ApiResponseDTO<List<MovieDTO>>> getLatestMovies() {
        List<MovieDTO> dtos = movieService.getLatestMovies().stream().map(MovieMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy phim mới nhất thành công", dtos));
    }

    @Operation(summary = "Get top rated movies", description = "Returns 10 top rated movies")
    @ApiResponse(responseCode = "200", description = "Top rated movies retrieved successfully")
    @GetMapping("/top-rated")
    public ResponseEntity<ApiResponseDTO<List<MovieDTO>>> getTopRatedMovies() {
        List<MovieDTO> dtos = movieService.getTopRatedMovies().stream().map(MovieMapper::toDTO).toList();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy phim được đánh giá cao thành công", dtos));
    }
}