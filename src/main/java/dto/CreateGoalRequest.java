package com.example.fitness_workout.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateGoalRequest {
    @NotNull
    private Long userId;

    @NotBlank(message = "Goal type is required")
    private String type;

    @NotNull
    @Positive(message = "Target must be greater than zero")
    private Double targetValue;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Double getTargetValue() { return targetValue; }
    public void setTargetValue(Double targetValue) { this.targetValue = targetValue; }
}
