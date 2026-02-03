package com.keisar.MovieBookingApplication.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowRequestDTO {
    private LocalDateTime showTime;
    private double price;
    private int movieId;
    private int theaterId;
}
