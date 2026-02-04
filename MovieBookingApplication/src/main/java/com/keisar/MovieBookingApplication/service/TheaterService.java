package com.keisar.MovieBookingApplication.service;

import com.keisar.MovieBookingApplication.dto.request.TheaterRequestDTO;
import com.keisar.MovieBookingApplication.dto.response.TheaterResponseDTO;
import com.keisar.MovieBookingApplication.exception.ResourceNotFoundException;
import com.keisar.MovieBookingApplication.model.Theater;
import com.keisar.MovieBookingApplication.repository.TheaterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheaterService {

    private final TheaterRepository theaterRepository;
    private final ModelMapper modelMapper;

    public TheaterService(TheaterRepository theaterRepository, ModelMapper modelMapper) {
        this.theaterRepository = theaterRepository;
        this.modelMapper = modelMapper;
    }

    public TheaterResponseDTO addTheater(TheaterRequestDTO theaterRequestDTO) {
        Theater theater = modelMapper.map(theaterRequestDTO, Theater.class);
        return modelMapper.map(theaterRepository.save(theater), TheaterResponseDTO.class);
    }

    public TheaterResponseDTO updateTheater(TheaterRequestDTO theaterRequestDTO, Integer id) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("O recurso com o ID solicitado não foi encontrado"));
        modelMapper.map(theaterRequestDTO, theater);
        return modelMapper.map(theaterRepository.save(theater), TheaterResponseDTO.class);
    }

    public void deleteById(Integer id) {
        if(!theaterRepository.existsById(id)){
            throw new ResourceNotFoundException("O recurso com o ID solicitado não foi encontrado");
        }
        theaterRepository.deleteById(id);
    }

    public List<TheaterResponseDTO> getTheaterByLocation(String location) {
        return theaterRepository.findByTheaterLocation(location).stream()
                .map(theater -> modelMapper.map(theater, TheaterResponseDTO.class))
                .collect(Collectors.toList());
    }
}
