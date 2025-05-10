package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.model.Movie;
import com.example.movieticketbookingbe.service.MovieService;
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
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.createMovie(movie));
    }

    @Operation(summary = "Update a movie", description = "Updates an existing movie by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movie updated successfully"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(
            @Parameter(description = "ID of the movie to update") @PathVariable Long id,
            @RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.updateMovie(id, movie));
    }

    @Operation(summary = "Delete a movie", description = "Deletes a movie by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movie deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(
            @Parameter(description = "ID of the movie to delete") @PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get a movie by ID", description = "Returns a movie by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movie found", content = @Content(schema = @Schema(implementation = Movie.class))),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(
            @Parameter(description = "ID of the movie to retrieve") @PathVariable Long id) {
        return movieService.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all movies", description = "Returns a list of all movies")
    @ApiResponse(responseCode = "200", description = "List of movies retrieved successfully", content = @Content(schema = @Schema(implementation = Movie.class)))
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @Operation(summary = "Search movies", description = "Search movies by title, genre, and language")
    @ApiResponse(responseCode = "200", description = "Search results retrieved successfully")
    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(
            @Parameter(description = "Movie title to search for") @RequestParam(required = false) String title,
            @Parameter(description = "Movie genre to filter by") @RequestParam(required = false) String genre,
            @Parameter(description = "Movie language to filter by") @RequestParam(required = false) String language) {
        return ResponseEntity.ok(movieService.searchMovies(title, genre, language));
    }

    @Operation(summary = "Get random movies", description = "Returns 3 random movies")
    @ApiResponse(responseCode = "200", description = "Random movies retrieved successfully")
    @GetMapping("/random")
    public ResponseEntity<List<Movie>> getRandomMovies() {
        return ResponseEntity.ok(movieService.getRandomMovies());
    }

    @Operation(summary = "Get latest movies", description = "Returns 10 latest movies")
    @ApiResponse(responseCode = "200", description = "Latest movies retrieved successfully")
    @GetMapping("/latest")
    public ResponseEntity<List<Movie>> getLatestMovies() {
        return ResponseEntity.ok(movieService.getLatestMovies());
    }

    @Operation(summary = "Get top rated movies", description = "Returns 10 top rated movies")
    @ApiResponse(responseCode = "200", description = "Top rated movies retrieved successfully")
    @GetMapping("/top-rated")
    public ResponseEntity<List<Movie>> getTopRatedMovies() {
        return ResponseEntity.ok(movieService.getTopRatedMovies());
    }
}