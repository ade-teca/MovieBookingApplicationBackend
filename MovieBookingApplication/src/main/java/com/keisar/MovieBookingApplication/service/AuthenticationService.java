package com.keisar.MovieBookingApplication.service;

import com.keisar.MovieBookingApplication.dto.request.LoginRequestDTO;
import com.keisar.MovieBookingApplication.dto.request.RegisterRequestDTO;
import com.keisar.MovieBookingApplication.dto.response.LoginResponseDTO;
import com.keisar.MovieBookingApplication.model.User;
import com.keisar.MovieBookingApplication.repository.UserRepository;
import com.keisar.MovieBookingApplication.security.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository,
                                 ModelMapper modelMapper,
                                 AuthenticationManager authenticationManager,
                                 JwtService jwtService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User registerNormalUser(RegisterRequestDTO registerRequestDTO) {
        if(userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already registered");
        }

        Set<String> role = new HashSet<>();
        role.add("ROLE_USER");

        User user = new User();
        user.setRoles(role);
        modelMapper.map(registerRequestDTO, user);
        return userRepository.save(user);
    }

    public User registerAdminUser(RegisterRequestDTO registerRequestDTO) {

        if(userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already registered");
        }
        Set<String> role = new HashSet<>();
        role.add("ROLE_ADMIN");
        role.add("ROLE_USER");

        User user = new User();
        user.setRoles(role);
        modelMapper.map(registerRequestDTO, user);
        return userRepository.save(user);
    }

    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(()-> new RuntimeException("Username is not registered"));

        authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(
                         loginRequestDTO.getUsername(),
                         loginRequestDTO.getPassword()
                 )
        );

        String token = jwtService.generateToken(user);

        return LoginResponseDTO
                .builder()
                .jwtToken(token)
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }
}
