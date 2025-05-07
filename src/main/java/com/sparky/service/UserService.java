package com.sparky.service;
import com.sparky.Domain.*;
import com.sparky.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserLimitRepository userLimitRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getUsersByCompany(Long companyId) {
        return userRepository.findByCompanyId(companyId);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User updated) {
        User user = userRepository.findById(id).orElseThrow();
        user.setUsername(updated.getUsername());
        user.setEmail(updated.getEmail());
        return userRepository.save(user);
    }

    public UserLimit assignUserLimit(Long userId, UserLimit limit) {
        User user = userRepository.findById(userId).orElseThrow();
        limit.setUser(user);
        return userLimitRepository.save(limit);
    }

    public List<UserLimit> getUserLimits(Long userId) {
        return userLimitRepository.findByUserId(userId);
    }
}
