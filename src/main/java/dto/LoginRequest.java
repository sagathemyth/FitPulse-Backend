package com.example.fitness_workout.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    // Accepts either the user's email address or their username.
    @NotBlank(message = "Email or username is required")
    private String identifier;

    @NotBlank
    private String password;

    public String getIdentifier() { return identifier; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}