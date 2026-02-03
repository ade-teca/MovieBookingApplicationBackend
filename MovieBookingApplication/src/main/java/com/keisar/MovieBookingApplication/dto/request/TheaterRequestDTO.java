package com.keisar.MovieBookingApplication.dto.request;

import lombok.Data;

@Data
public class TheaterRequestDTO {


    private String theaterName;
    private String theaterLocation;
    private Long theaterCapacity;
    private String theaterScreenType;
}
