package com.keisar.MovieBookingApplication.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter

public class ErrorResponse {
    private LocalDateTime timestamp;
    private String errorMessage;
    private String errorDetails;
    private String errorCode;

    public ErrorResponse(String errorMessage, String errorDetails, String errorCode) {
        this.timestamp = LocalDateTime.now();
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
        this.errorCode = errorCode;
    }
}
