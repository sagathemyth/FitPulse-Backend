package com.example.fitness_workout.dto;

public class GoalResponse {
    private Long id;
    private String type;
    private double targetValue;
    private double currentValue;
    private boolean achieved;

    public GoalResponse(Long id, String type, double targetValue, double currentValue, boolean achieved) {
        this.id = id; this.type = type; this.targetValue = targetValue;
        this.currentValue = currentValue; this.achieved = achieved;
    }

    public Long getId() { return id; }
    public String getType() { return type; }
    public double getTargetValue() { return targetValue; }
    public double getCurrentValue() { return currentValue; }
    public boolean isAchieved() { return achieved; }
}
