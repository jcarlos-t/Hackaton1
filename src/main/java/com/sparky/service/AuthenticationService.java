package com.sparky.service;

import com.sparky.service.JwtService;
import com.sparky.Domain.User;
import com.sparky.dto.AuthenticationRequest;
import com.sparky.dto.AuthenticationResponse;
import com.sparky.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse login(AuthenticationRequest request) {
        try {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }

            String jwt = jwtService.generateToken(user);
            return new AuthenticationResponse(jwt, user.getRole().name());

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
