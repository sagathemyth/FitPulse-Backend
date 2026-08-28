package com.example.fitness_workout.repository;

import com.example.fitness_workout.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}