package com.example.fitness_workout.dto;

import java.time.LocalDate;
import java.util.Map;

public class ProgressResponse {
    private int totalWorkouts;
    private double caloriesThisWeek;
    private Map<LocalDate, Double> dailyCalories;

    public ProgressResponse(int totalWorkouts, double caloriesThisWeek, Map<LocalDate, Double> dailyCalories) {
        this.totalWorkouts = totalWorkouts;
        this.caloriesThisWeek = caloriesThisWeek;
        this.dailyCalories = dailyCalories;
    }

    public int getTotalWorkouts() { return totalWorkouts; }
    public double getCaloriesThisWeek() { return caloriesThisWeek; }
    public Map<LocalDate, Double> getDailyCalories() { return dailyCalories; }
}