package com.keisar.MovieBookingApplication.service;


import com.keisar.MovieBookingApplication.dto.request.MovieRequestDTO;
import com.keisar.MovieBookingApplication.dto.response.MovieResponseDTO;
import com.keisar.MovieBookingApplication.exception.ResourceNotFoundException;
import com.keisar.MovieBookingApplication.model.Movie;
import com.keisar.MovieBookingApplication.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    public MovieService(MovieRepository movieRepository, ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
    }

    public List<MovieResponseDTO> getAll() {
        return movieRepository.findAll().stream()
                .map(movie -> modelMapper.map(movie, MovieResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<MovieResponseDTO> getAllByGenre(String genre) {
        return movieRepository.findMovieByGenre(genre).stream()
                .map(movie -> modelMapper.map(movie, MovieResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<MovieResponseDTO> getAllByLanguage(String language) {
        return movieRepository.findMovieByLanguage(language).stream()
                .map(movie -> modelMapper.map(movie, MovieResponseDTO.class))
                .collect(Collectors.toList());
    }

    public MovieResponseDTO getMovieByTitle(String title) {
        Movie movie = movieRepository.findMovieByTitle(title).stream().findFirst()
                .orElseThrow(()-> new RuntimeException("Not found"));
        return modelMapper.map(movie, MovieResponseDTO.class);
    }

    public MovieResponseDTO addMovie(MovieRequestDTO movieRequest) {
        Movie movie = modelMapper.map(movieRequest, Movie.class);
        return modelMapper.map(movieRepository.save(movie), MovieResponseDTO.class);
    }

    public MovieResponseDTO updateMovie(MovieRequestDTO movieRequest, Integer id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("O recurso com o ID solicitado não foi encontrado"));
        modelMapper.map(movieRequest, movie);
        return modelMapper.map(movieRepository.save(movie), MovieResponseDTO.class);
    }

    public void deleteMovie(Integer id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("O recurso com o ID solicitado não foi encontrado");
        }
        movieRepository.deleteById(id);
    }





}
