package com.keisar.MovieBookingApplication.dto.response;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowResponseDTO {
    private Long showId;
    private LocalDateTime showTime;
    private double price;
    private String movieName;    // Better for the frontend
    private String theaterName;  // Better for the frontend
}
