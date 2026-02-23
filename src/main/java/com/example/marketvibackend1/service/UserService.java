package com.example.marketvibackend1.service;

import com.example.marketvibackend1.controller.dto.RegisterRequests;
import com.example.marketvibackend1.model.User;
import com.example.marketvibackend1.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(RegisterRequests req) {
        if (req.password == null || req.password.isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }

        User user = new User();
        user.setEmail(req.email);
        user.setFirstName(req.firstName);
        user.setLastName(req.lastName);
        user.setPasswordHash(passwordEncoder.encode(req.password));

        return userRepository.save(user);
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> deleteUserById(long id) {
        Optional<User> userOpt = userRepository.findById(id);
        userOpt.ifPresent(u -> userRepository.deleteById(id));
        return userOpt;
    }

    public User updateUser(long id, User updated) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));

        existing.setEmail(updated.getEmail());
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());

        if (updated.getPasswordHash() != null && !updated.getPasswordHash().isBlank()) {
            existing.setPasswordHash(passwordEncoder.encode(updated.getPasswordHash()));
        }

        return userRepository.save(existing);
    }

}
