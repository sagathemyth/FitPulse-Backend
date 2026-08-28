package com.example.fitness_workout.service;
import com.example.fitness_workout.dto.UpdateProfileRequest;

import com.example.fitness_workout.dto.ChangePasswordRequest;
import com.example.fitness_workout.dto.LoginRequest;
import com.example.fitness_workout.dto.RegisterRequest;
import com.example.fitness_workout.dto.ResetPasswordRequest;
import com.example.fitness_workout.dto.UserResponse;
import com.example.fitness_workout.model.AppUser;
import com.example.fitness_workout.repository.AppUserRepository;
import com.example.fitness_workout.repository.GoalRepository;
import com.example.fitness_workout.repository.WorkoutSessionRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppUserService {

    private final AppUserRepository userRepository;
    private final WorkoutSessionRepository sessionRepository;
    private final GoalRepository goalRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AppUserService(AppUserRepository userRepository, WorkoutSessionRepository sessionRepository, GoalRepository goalRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.goalRepository = goalRepository;
    }

    public UserResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }

        AppUser user = new AppUser();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        AppUser saved = userRepository.save(user);
        return toResponse(saved);
    }

    public UserResponse updateProfile(Long userId, UpdateProfileRequest request) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userRepository.findByEmail(request.getEmail()).ifPresent(existing -> {
            if (!existing.getId().equals(userId)) {
                throw new IllegalArgumentException("Email already registered");
            }
        });

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
        user.setBiologicalSex(request.getBiologicalSex());
        user.setHeightCm(request.getHeightCm());
        user.setWeightKg(request.getWeightKg());

        AppUser saved = userRepository.save(user);
        return toResponse(saved);
    }

    public UserResponse login(LoginRequest request) {
        AppUser user = userRepository.findByEmailOrUsername(request.getIdentifier(), request.getIdentifier())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email/username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email/username or password");
        }

        return toResponse(user);
    }

    private UserResponse toResponse(AppUser user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getUsername(), user.getCreatedAt(),
                user.getAge(), user.getBiologicalSex(), user.getHeightCm(), user.getWeightKg());
    }

    public void changePassword(Long userId, ChangePasswordRequest request) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public void verifyEmailExists(String email) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No account found with that email"));
    }

    public void resetPassword(ResetPasswordRequest request) {
        AppUser user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("No account found with that email"));
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public void deleteAccount(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }
        // Workout sessions cascade-delete their exercises (orphanRemoval on
        // WorkoutSession.exercises), so deleting the sessions is enough there.
        sessionRepository.deleteAll(sessionRepository.findByUserId(userId));
        goalRepository.deleteAll(goalRepository.findByUserId(userId));
        userRepository.deleteById(userId);
    }
}