package com.example.marketvibackend1.controller;

import com.example.marketvibackend1.controller.dto.RegisterRequests;
import com.example.marketvibackend1.controller.dto.UpdateUserRequest;
import com.example.marketvibackend1.controller.dto.UserResponse;
import com.example.marketvibackend1.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody RegisterRequests req) {
        return userService.createUser(req);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable long id, @RequestBody UpdateUserRequest req) {
        return userService.updateUser(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUserById(@PathVariable long id) {
        return userService.deleteUserById(id);
    }
}
