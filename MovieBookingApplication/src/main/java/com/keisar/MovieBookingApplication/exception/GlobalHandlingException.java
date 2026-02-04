package com.keisar.MovieBookingApplication.exception;

import com.keisar.MovieBookingApplication.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalHandlingException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(),
                request.getDescription(false),
                "Resource Not Found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(
            UserAlreadyExistsException exception,
            WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(),
                request.getDescription(false),
                "User Already Registered");
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SeatAlreadyOccupiedException.class)
    public ResponseEntity<ErrorResponse> handleSeatAlreadyOccupiedException(
            SeatAlreadyOccupiedException exception,
            WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(),
                request.getDescription(false),
                "Seat Selection Conflict");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidBookingStateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidBookingStateException(
            InvalidBookingStateException exception,
            WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(),
                request.getDescription(false),
                "Invalid Booking Operation");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CancellationException.class)
    public ResponseEntity<ErrorResponse> handleCancellationException(
            CancellationException exception,
            WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(),
                request.getDescription(false),
                "Cancellation Policy Violation");
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(
            InvalidCredentialsException exception,
            WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(),
                request.getDescription(false),
                "Authentication Failed");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException exception,
            WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(),
                request.getDescription(false),
                "Data Integrity Constraint");
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}