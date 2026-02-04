package com.keisar.MovieBookingApplication.exception;

public class DataIntegrityViolationException extends RuntimeException {
    public  DataIntegrityViolationException(String message) {
        super(message);
    }
}
