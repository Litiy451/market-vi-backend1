package com.example.marketvibackend1.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
}
