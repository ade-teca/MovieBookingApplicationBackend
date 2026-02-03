package com.keisar.MovieBookingApplication.controller;

import com.keisar.MovieBookingApplication.dto.request.TheaterRequestDTO;
import com.keisar.MovieBookingApplication.dto.response.TheaterResponseDTO;
import com.keisar.MovieBookingApplication.service.TheaterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
public class TheaterController {

    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TheaterResponseDTO> createTheater(@RequestBody TheaterRequestDTO theaterRequestDTO) {
        return ResponseEntity.status(201).body(theaterService.addTheater(theaterRequestDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TheaterResponseDTO> updateTheater(@RequestBody TheaterRequestDTO theaterRequestDTO,
                                                            @PathVariable Integer id) {
        return ResponseEntity.ok().body(theaterService.updateTheater(theaterRequestDTO, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTheater(@PathVariable Integer id) {
        theaterService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/location") // Shortened path for clarity
    public ResponseEntity<List<TheaterResponseDTO>> getTheatersByLocation(@RequestParam String location) {
        List<TheaterResponseDTO> theaters = theaterService.getTheaterByLocation(location);
        return ResponseEntity.ok(theaters);
    }
}
