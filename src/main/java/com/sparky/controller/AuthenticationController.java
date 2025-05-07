package com.sparky.controller;

import com.sparky.Domain.User;
import com.sparky.dto.AuthenticationRequest;
import com.sparky.dto.AuthenticationResponse;
import com.sparky.repository.UserRepository;
import com.sparky.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // 🔓 Ruta pública para registrar un SPARKY_ADMIN solo por fines de test
    @PostMapping("/register-admin")
    public ResponseEntity<?> registerSparkyAdmin(@RequestBody User user) {
        user.setRole(User.Role.SPARKY_ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userRepository.save(user));
    }
}
