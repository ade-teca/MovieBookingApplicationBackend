package com.keisar.MovieBookingApplication.controller;

import com.keisar.MovieBookingApplication.dto.request.BookingRequestDTO;
import com.keisar.MovieBookingApplication.dto.response.BookingResponseDTO;
import com.keisar.MovieBookingApplication.model.BookingStatus;
import com.keisar.MovieBookingApplication.repository.BookingRepository;
import com.keisar.MovieBookingApplication.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
        return ResponseEntity.ok().body(bookingService.addBooking(bookingRequestDTO));
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<BookingResponseDTO> confirmBooking(@PathVariable int id) {
        return ResponseEntity.ok().body(bookingService.confirmBooking(id));
    }

    @PutMapping("/{id}/cancell")
    public ResponseEntity<BookingResponseDTO> cancelBooking(@PathVariable int id) {
        return ResponseEntity.ok().body(bookingService.cancellBooking(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponseDTO>> getAllUserBookings(@PathVariable Integer userId) {
        return ResponseEntity.ok().body(bookingService.getAllUserBookings(userId));
    }

    @GetMapping("/shows/{showId}")
    public ResponseEntity<List<BookingResponseDTO>> getAllShowBookings(@PathVariable Integer userId) {
        return ResponseEntity.ok().body(bookingService.getAllShowBookings(userId));
    }

    @GetMapping("/bookingstatus/{bookingStatus}")
    public ResponseEntity<List<BookingResponseDTO>> getAllBookingStatus(@PathVariable BookingStatus bookingstatus) {
        return ResponseEntity.ok().body(bookingService.getAllBookingStatus(bookingstatus));
    }




}
