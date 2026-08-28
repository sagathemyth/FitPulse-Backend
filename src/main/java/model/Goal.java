package com.example.fitness_workout.model;

import jakarta.persistence.*;

@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private double targetValue;
    private double currentValue;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    public boolean isAchieved() {
        return currentValue >= targetValue;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getTargetValue() { return targetValue; }
    public void setTargetValue(double targetValue) { this.targetValue = targetValue; }
    public double getCurrentValue() { return currentValue; }
    public void setCurrentValue(double currentValue) { this.currentValue = currentValue; }
    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }
}
