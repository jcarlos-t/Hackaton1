package com.sparky.controller;

import com.sparky.Domain.User;
import com.sparky.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/init")
@RequiredArgsConstructor
public class InitController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/admin")
    public ResponseEntity<String> createSparkyAdmin() {
        if (userRepository.findByEmail("admin@sparky.com").isPresent()) {
            return ResponseEntity.badRequest().body("Ya existe el admin.");
        }

        User admin = User.builder()
                .username("admin")
                .email("admin@sparky.com")
                .password(passwordEncoder.encode("admin123"))
                .role(User.Role.SPARKY_ADMIN)
                .build();

        userRepository.save(admin);
        return ResponseEntity.ok("SPARKY_ADMIN creado con éxito 🎉");
    }
}
