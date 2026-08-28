package com.example.fitness_workout.dto;

import java.time.LocalDate;

public class WorkoutSessionSummaryResponse {
    private Long id;
    private String title;
    private LocalDate date;
    private double totalCalories;
    private int exerciseCount;

    public WorkoutSessionSummaryResponse(Long id, String title, LocalDate date, double totalCalories, int exerciseCount) {
        this.id = id; this.title = title; this.date = date;
        this.totalCalories = totalCalories; this.exerciseCount = exerciseCount;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public LocalDate getDate() { return date; }
    public double getTotalCalories() { return totalCalories; }
    public int getExerciseCount() { return exerciseCount; }
}