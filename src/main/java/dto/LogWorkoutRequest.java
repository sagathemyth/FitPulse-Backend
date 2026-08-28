package com.example.fitness_workout.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

public class LogWorkoutRequest {
    @NotNull
    private Long userId;

    private String sessionTitle; // used only if creating a new session

    @NotNull
    private LocalDate date;

    @NotBlank
    private String exerciseName;

    @NotBlank
    private String exerciseType; // "CARDIO" or "STRENGTH"

    private String notes;

    // Cardio fields
    @PositiveOrZero(message = "Duration cannot be negative")
    private Integer durationMinutes;
    @PositiveOrZero(message = "Distance cannot be negative")
    private Double distanceKm;

    // Strength fields
    @PositiveOrZero(message = "Sets cannot be negative")
    private Integer sets;
    @PositiveOrZero(message = "Reps cannot be negative")
    private Integer reps;
    @PositiveOrZero(message = "Weight cannot be negative")
    private Double weightKg;

    // Getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getSessionTitle() { return sessionTitle; }
    public void setSessionTitle(String sessionTitle) { this.sessionTitle = sessionTitle; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }
    public String getExerciseType() { return exerciseType; }
    public void setExerciseType(String exerciseType) { this.exerciseType = exerciseType; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }
    public Integer getSets() { return sets; }
    public void setSets(Integer sets) { this.sets = sets; }
    public Integer getReps() { return reps; }
    public void setReps(Integer reps) { this.reps = reps; }
    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }
}