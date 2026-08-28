package com.example.fitness_workout.dto;

import java.time.LocalDate;
import java.util.List;

public class WorkoutSessionDetailResponse {
    private Long id;
    private String title;
    private LocalDate date;
    private double totalCalories;
    private List<ExerciseResponse> exercises;

    public WorkoutSessionDetailResponse(Long id, String title, LocalDate date, double totalCalories, List<ExerciseResponse> exercises) {
        this.id = id; this.title = title; this.date = date;
        this.totalCalories = totalCalories; this.exercises = exercises;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public LocalDate getDate() { return date; }
    public double getTotalCalories() { return totalCalories; }
    public List<ExerciseResponse> getExercises() { return exercises; }
}