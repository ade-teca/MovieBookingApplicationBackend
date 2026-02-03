package com.keisar.MovieBookingApplication.dto.request;

import lombok.Data;

@Data
public class RegisterRequestDTO {

    private String username;
    private String password;
    private String email;
}
