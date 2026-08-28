package com.example.fitness_workout.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("STRENGTH")
public class StrengthExercise extends Exercise {

    private int sets;
    private int reps;
    private double weightKg;

    @Override
    public double caloriesBurned() {
        // Simple formula: sets * reps * weight factor
        return sets * reps * (weightKg * 0.1);
    }

    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }
    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }
    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }
}