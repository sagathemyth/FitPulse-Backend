package com.example.fitness_workout.dto;

import java.time.LocalDate;

public class ExerciseResponse {
    private Long id;
    private String name;
    private String type;
    private LocalDate date;
    private String notes;
    private double caloriesBurned;
    private Integer durationMinutes;
    private Double distanceKm;
    private Integer sets;
    private Integer reps;
    private Double weightKg;

    public ExerciseResponse(Long id, String name, String type, LocalDate date, String notes,
                            double caloriesBurned, Integer durationMinutes, Double distanceKm,
                            Integer sets, Integer reps, Double weightKg) {
        this.id = id; this.name = name; this.type = type; this.date = date; this.notes = notes;
        this.caloriesBurned = caloriesBurned; this.durationMinutes = durationMinutes;
        this.distanceKm = distanceKm; this.sets = sets; this.reps = reps; this.weightKg = weightKg;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public LocalDate getDate() { return date; }
    public String getNotes() { return notes; }
    public double getCaloriesBurned() { return caloriesBurned; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public Double getDistanceKm() { return distanceKm; }
    public Integer getSets() { return sets; }
    public Integer getReps() { return reps; }
    public Double getWeightKg() { return weightKg; }
}