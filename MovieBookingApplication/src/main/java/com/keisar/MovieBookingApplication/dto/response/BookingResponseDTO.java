package com.keisar.MovieBookingApplication.dto.response;

import com.keisar.MovieBookingApplication.model.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingResponseDTO {

    private Long bookingId;
    private double totalAmount;
    private LocalDateTime bookingTime;
    private Integer numberOfSeats;
    private BookingStatus bookingStatus;
    private List<String> seatNumbers;
    private Long userId;
    private Long showId;
}
