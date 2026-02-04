package com.keisar.MovieBookingApplication.exception;

public class CancellationException extends  RuntimeException{
    public CancellationException(String message) {
        super(message);
    }
}
