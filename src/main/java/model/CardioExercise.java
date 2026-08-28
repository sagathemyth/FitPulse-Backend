package com.example.fitness_workout.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CARDIO")
public class CardioExercise extends Exercise {

    private int durationMinutes;
    private double distanceKm;

    @Override
    public double caloriesBurned() {
        // Simple formula: ~10 kcal per minute, adjusted slightly by distance
        return (durationMinutes * 10) + (distanceKm * 5);
    }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    public double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }
}