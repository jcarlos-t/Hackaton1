package com.sparky.service;
import com.sparky.Domain.*;
import com.sparky.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLimitRepository userLimitRepository;
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User createUser(User user) {
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
