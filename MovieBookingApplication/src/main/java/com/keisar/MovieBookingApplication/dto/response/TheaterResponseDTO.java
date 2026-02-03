package com.keisar.MovieBookingApplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheaterResponseDTO {

    private Long id;
    private String theaterName;
    private String theaterLocation;
    private Long theaterCapacity;
    private String theaterScreenType;
}
