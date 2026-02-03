package com.keisar.MovieBookingApplication.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieRequestDTO {
    private String movieName;
    private String movieDescription;
    private Integer duration;
    private String movieGenre;
    private String movieLanguage;
    private LocalDate movieReleaseDate;
}
