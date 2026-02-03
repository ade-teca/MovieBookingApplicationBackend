package com.keisar.MovieBookingApplication.controller;

import com.keisar.MovieBookingApplication.dto.request.MovieRequestDTO;
import com.keisar.MovieBookingApplication.dto.response.MovieResponseDTO;
import com.keisar.MovieBookingApplication.model.Movie;
import com.keisar.MovieBookingApplication.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // Single search endpoint: /api/movies/search?genre=Action&lang=English
    @GetMapping("/search")
    public ResponseEntity<List<MovieResponseDTO>> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String language) {

        if (title != null) {
            // Note: Returning a List here is safer than a single object for searches
            return ResponseEntity.ok(List.of(movieService.getMovieByTitle(title)));
        } else if (genre != null) {
            return ResponseEntity.ok(movieService.getAllByGenre(genre));
        } else if (language != null) {
            return ResponseEntity.ok(movieService.getAllByLanguage(language));
        }

        return ResponseEntity.ok(movieService.getAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieResponseDTO> createMovie(@RequestBody MovieRequestDTO movieRequestDTO) {
        return ResponseEntity.ok().body(movieService.addMovie(movieRequestDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieResponseDTO> updateMovie(@RequestBody MovieRequestDTO movieRequestDTO,  @PathVariable Integer id) {
        return ResponseEntity.status(201).body(movieService.updateMovie(movieRequestDTO, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
