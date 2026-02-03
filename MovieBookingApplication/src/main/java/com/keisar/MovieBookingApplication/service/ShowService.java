package com.keisar.MovieBookingApplication.service;

import com.keisar.MovieBookingApplication.dto.request.ShowRequestDTO;
import com.keisar.MovieBookingApplication.dto.response.MovieResponseDTO;
import com.keisar.MovieBookingApplication.dto.response.ShowResponseDTO;
import com.keisar.MovieBookingApplication.model.Booking;
import com.keisar.MovieBookingApplication.model.Movie;
import com.keisar.MovieBookingApplication.model.Show;
import com.keisar.MovieBookingApplication.model.Theater;
import com.keisar.MovieBookingApplication.repository.MovieRepository;
import com.keisar.MovieBookingApplication.repository.ShowRepository;
import com.keisar.MovieBookingApplication.repository.TheaterRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;
    private final ModelMapper modelMapper;

    public ShowService(ShowRepository showRepository, MovieRepository movieRepository,
                       TheaterRepository theaterRepository, ModelMapper modelMapper) {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
        this.modelMapper = modelMapper;
    }


    public ShowResponseDTO addShow(ShowRequestDTO showRequestDTO) {
        // 1. Fetch related entities to ensure they exist and link them
        Movie movie = movieRepository.findById(showRequestDTO.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        Theater theater = theaterRepository.findById(showRequestDTO.getTheaterId())
                .orElseThrow(() -> new RuntimeException("Theater not found"));

        // 2. Map basic fields
        Show show = modelMapper.map(showRequestDTO, Show.class);

        // 3. Manually set the relationships
        show.setMovie(movie);
        show.setTheater(theater);

        // 4. Save and return mapped response
        Show savedShow = showRepository.save(show);
        return modelMapper.map(savedShow, ShowResponseDTO.class);
    }

    public ShowResponseDTO updateShow(ShowRequestDTO showRequestDTO, int id) {
        Show show = showRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("show not found"));
        modelMapper.map(showRequestDTO, show);
        return modelMapper.map(showRepository.save(show), ShowResponseDTO.class);
    }

    public void deleteShowById(int id) {
        if (!showRepository.existsById(id)) {
            throw new RuntimeException("show not found");
        }

        List<Booking> bookings = showRepository.findById(id).get().getBookings();
        if (!bookings.isEmpty()) {
            throw new RuntimeException("bookings not empty");
        }
        showRepository.deleteById(id);
    }

    public List<ShowResponseDTO> getShowByMovie(Integer movieId) {
        return showRepository.findByMovie_Id(movieId).stream() // Use underscore for nested property
                .map(show -> modelMapper.map(show, ShowResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<ShowResponseDTO> getShowByTheater(Integer theaterId) {
        // Fixed: Now calling the correct Theater relationship
        return showRepository.findByTheater_Id(theaterId).stream()
                .map(show -> modelMapper.map(show, ShowResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<ShowResponseDTO> getAll() {
        return showRepository.findAll().stream()
                .map(show -> modelMapper.map(show, ShowResponseDTO.class))
                .collect(Collectors.toList());
    }
}
