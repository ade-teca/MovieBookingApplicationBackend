package com.keisar.MovieBookingApplication.exception;

public class InvalidBookingStateException extends RuntimeException{
    public  InvalidBookingStateException(String message) {
        super(message);
    }
}
