package com.example.marketvibackend1.service;

import com.example.marketvibackend1.controller.dto.UserResponse;
import com.example.marketvibackend1.model.User;

public class UserMapper {

    private UserMapper() {}

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
