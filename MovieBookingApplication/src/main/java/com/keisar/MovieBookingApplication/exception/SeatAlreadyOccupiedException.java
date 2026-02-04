package com.keisar.MovieBookingApplication.exception;

public class SeatAlreadyOccupiedException extends RuntimeException {
    public  SeatAlreadyOccupiedException(String message) {
        super(message);
    }
}
