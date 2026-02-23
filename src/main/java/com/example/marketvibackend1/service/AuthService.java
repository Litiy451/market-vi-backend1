package com.example.marketvibackend1.service;

import com.example.marketvibackend1.controller.dto.AuthResponse;
import com.example.marketvibackend1.controller.dto.LoginRequest;
import com.example.marketvibackend1.controller.dto.UserResponse;
import com.example.marketvibackend1.model.User;
import com.example.marketvibackend1.repository.UserRepository;
import com.example.marketvibackend1.service.JwtService;
import com.example.marketvibackend1.service.UserMapper;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public ResponseEntity<AuthResponse> login(LoginRequest req) {
        if (req == null || req.email == null || req.email.isBlank()
                || req.password == null || req.password.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        User user = userRepository.findByEmail(req.email).orElse(null);

        if (user == null || !passwordEncoder.matches(req.password, user.getPasswordHash())) {
            return ResponseEntity.status(401).build();
        }

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getId());

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofDays(14))
                .build();

        UserResponse userDto = UserMapper.toResponse(user);
        AuthResponse body = new AuthResponse(accessToken, userDto);

        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .body(body);
    }

    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .build();
    }
}
