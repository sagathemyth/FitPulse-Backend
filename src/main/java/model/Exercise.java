package com.example.fitness_workout.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "exercises")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "exercise_type")
public abstract class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate date;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private WorkoutSession workoutSession;

    // Abstract method — each subclass implements its own calorie logic
    public abstract double caloriesBurned();

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public WorkoutSession getWorkoutSession() { return workoutSession; }
    public void setWorkoutSession(WorkoutSession workoutSession) { this.workoutSession = workoutSession; }
}