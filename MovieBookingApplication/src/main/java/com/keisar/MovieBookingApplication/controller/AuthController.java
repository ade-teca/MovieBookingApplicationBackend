package com.keisar.MovieBookingApplication.controller;

import com.keisar.MovieBookingApplication.dto.request.LoginRequestDTO;
import com.keisar.MovieBookingApplication.dto.request.RegisterRequestDTO;
import com.keisar.MovieBookingApplication.dto.response.LoginResponseDTO;
import com.keisar.MovieBookingApplication.model.User;
import com.keisar.MovieBookingApplication.repository.UserRepository;
import com.keisar.MovieBookingApplication.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registernormaluser")
    public ResponseEntity<User> registerNormalUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.status(201).body(authenticationService.registerNormalUser(registerRequestDTO));
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok().body(authenticationService.loginUser(loginRequestDTO));
    }





}
