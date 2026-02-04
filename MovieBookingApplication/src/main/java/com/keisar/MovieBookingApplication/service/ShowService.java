package com.keisar.MovieBookingApplication.service;

import com.keisar.MovieBookingApplication.dto.request.ShowRequestDTO;
import com.keisar.MovieBookingApplication.dto.response.MovieResponseDTO;
import com.keisar.MovieBookingApplication.dto.response.ShowResponseDTO;
import com.keisar.MovieBookingApplication.exception.DataIntegrityViolationException;
import com.keisar.MovieBookingApplication.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("O recurso com o ID solicitado não foi encontrado"));
        Theater theater = theaterRepository.findById(showRequestDTO.getTheaterId())
                .orElseThrow(() -> new ResourceNotFoundException("O recurso com o ID solicitado não foi encontrado"));

        // 2. Map basic fields
        Show show = modelMapper.map(showRequestDTO, Show.class);

        // 3. Manually set the relationships
        show.setMovie(movie);
        show.setTheater(theater);

        // 4. Save and return mapped response
        Show savedShow = showRepository.save(show);
        return modelMapper.map(savedShow, ShowResponseDTO.class);
    }

    public ShowResponseDTO updateShow(ShowRequestDTO showRequestDTO, Long id) {
        Show show = showRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("O recurso com o ID solicitado não foi encontrado"));
        modelMapper.map(showRequestDTO, show);
        return modelMapper.map(showRepository.save(show), ShowResponseDTO.class);
    }

    public void deleteShowById(Long id) {
        if (!showRepository.existsById(id)) {
            throw new DataIntegrityViolationException("Não é possível deletar um show que possui reservas ativas.");
        }

        List<Booking> bookings = showRepository.findById(id).get().getBookings();
        if (!bookings.isEmpty()) {
            throw new ResourceNotFoundException("O recurso com o ID solicitado não foi encontrado");
        }
        showRepository.deleteById(id);
    }

    public List<ShowResponseDTO> getShowByMovie(Integer movieId) {
        return showRepository.findByMovie_MovieId(movieId).stream() // Use underscore for nested property
                .map(show -> modelMapper.map(show, ShowResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<ShowResponseDTO> getShowByTheater(Integer theaterId) {
        // Fixed: Now calling the correct Theater relationship
        return showRepository.findByTheater_TheaterId(theaterId).stream()
                .map(show -> modelMapper.map(show, ShowResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<ShowResponseDTO> getAll() {
        return showRepository.findAll().stream()
                .map(show -> modelMapper.map(show, ShowResponseDTO.class))
                .collect(Collectors.toList());
    }
}
