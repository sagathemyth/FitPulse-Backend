package com.example.fitness_workout.controller;

import com.example.fitness_workout.dto.ChangePasswordRequest;
import com.example.fitness_workout.dto.UpdateProfileRequest;
import com.example.fitness_workout.dto.UserResponse;
import com.example.fitness_workout.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final AppUserService userService;

    public UserController(AppUserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateProfile(@PathVariable Long id, @Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(userService.updateProfile(id, request));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        userService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}