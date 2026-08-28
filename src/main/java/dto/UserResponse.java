package com.example.fitness_workout.dto;

import java.time.LocalDateTime;

public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String username;
    private LocalDateTime createdAt;
    private Integer age;
    private String biologicalSex;
    private Double heightCm;
    private Double weightKg;

    public UserResponse(Long id, String name, String email, String username, LocalDateTime createdAt,
                         Integer age, String biologicalSex, Double heightCm, Double weightKg) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.createdAt = createdAt;
        this.age = age;
        this.biologicalSex = biologicalSex;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Integer getAge() { return age; }
    public String getBiologicalSex() { return biologicalSex; }
    public Double getHeightCm() { return heightCm; }
    public Double getWeightKg() { return weightKg; }
}