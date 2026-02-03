package com.keisar.MovieBookingApplication.controller;

import com.keisar.MovieBookingApplication.dto.request.ShowRequestDTO;
import com.keisar.MovieBookingApplication.dto.response.MovieResponseDTO;
import com.keisar.MovieBookingApplication.dto.response.ShowResponseDTO;
import com.keisar.MovieBookingApplication.service.ShowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/show")
public class ShowController {

    private final ShowService showService;
    public ShowController(ShowService showService) {this.showService = showService;}

    @PostMapping
    public ResponseEntity<ShowResponseDTO> createShow(@RequestBody ShowRequestDTO showRequestDTO) {
        return ResponseEntity.ok().body(showService.addShow(showRequestDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ShowResponseDTO> updateShow(@RequestBody ShowRequestDTO showRequestDTO,@PathVariable int id) {
        return ResponseEntity.ok().body(showService.updateShow(showRequestDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShow(@PathVariable int id) {
        showService.deleteShowById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/search")
    public ResponseEntity<List<ShowResponseDTO>> searchShows(
            @RequestParam(required = false) Integer movieId,
            @RequestParam(required = false) Integer theaterId) {

        if (movieId != null) {
            return ResponseEntity.ok(showService.getShowByMovie(movieId));
        }
        if (theaterId != null) {
            return ResponseEntity.ok(showService.getShowByTheater(theaterId));
        }
        return ResponseEntity.ok(showService.getAll());
    }


}
