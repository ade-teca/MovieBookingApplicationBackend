package com.keisar.MovieBookingApplication.dto.request;

import com.keisar.MovieBookingApplication.model.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingRequestDTO {

    private Integer numberOfSeats;
    private List<String> seatNumbers;
    private Long userId;
    private Long showId;
}
