package com.example.marketvibackend1.service;

import com.example.marketvibackend1.controller.dto.RegisterRequests;
import com.example.marketvibackend1.controller.dto.UserResponse;
import com.example.marketvibackend1.controller.dto.UpdateUserRequest;
import com.example.marketvibackend1.model.User;
import com.example.marketvibackend1.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<UserResponse> createUser(RegisterRequests req) {
        if (req == null || req.password == null || req.password.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        User user = new User();
        user.setEmail(req.email);
        user.setFirstName(req.firstName);
        user.setLastName(req.lastName);
        user.setPasswordHash(passwordEncoder.encode(req.password));

        User saved = userRepository.save(user);
        return ResponseEntity.ok(UserMapper.toResponse(saved));
    }

    public ResponseEntity<UserResponse> getUserById(long id) {
        return userRepository.findById(id)
                .map(u -> ResponseEntity.ok(UserMapper.toResponse(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<UserResponse> updateUser(long id, UpdateUserRequest req) {
        if (req == null) {
            return ResponseEntity.badRequest().build();
        }

        return userRepository.findById(id)
                .map(existing -> {
                    if (req.email != null) existing.setEmail(req.email);
                    if (req.firstName != null) existing.setFirstName(req.firstName);
                    if (req.lastName != null) existing.setLastName(req.lastName);

                    if (req.password != null && !req.password.isBlank()) {
                        existing.setPasswordHash(passwordEncoder.encode(req.password));
                    }

                    User saved = userRepository.save(existing);
                    return ResponseEntity.ok(UserMapper.toResponse(saved));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<UserResponse> deleteUserById(long id) {
        return userRepository.findById(id)
                .map(u -> {
                    userRepository.deleteById(id);
                    return ResponseEntity.ok(UserMapper.toResponse(u));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
