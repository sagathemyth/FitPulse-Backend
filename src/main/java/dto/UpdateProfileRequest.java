package com.example.fitness_workout.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class UpdateProfileRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @Min(value = 1, message = "Age must be positive")
    @Max(value = 120, message = "Age must be realistic")
    private Integer age;

    private String biologicalSex;

    @DecimalMin(value = "1", message = "Height must be positive")
    @DecimalMax(value = "300", message = "Height must be realistic")
    private Double heightCm;

    @DecimalMin(value = "1", message = "Weight must be positive")
    @DecimalMax(value = "500", message = "Weight must be realistic")
    private Double weightKg;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getBiologicalSex() { return biologicalSex; }
    public void setBiologicalSex(String biologicalSex) { this.biologicalSex = biologicalSex; }
    public Double getHeightCm() { return heightCm; }
    public void setHeightCm(Double heightCm) { this.heightCm = heightCm; }
    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }
}