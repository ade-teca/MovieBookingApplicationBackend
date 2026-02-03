package com.keisar.MovieBookingApplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponseDTO {
    private int movieId;
    private String movieName;
    private String movieDescription;
    private Integer duration;
    private String movieGenre;
    private String movieLanguage;
    private LocalDate movieReleaseDate;
}
