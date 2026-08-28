package com.example.fitness_workout.repository;

import com.example.fitness_workout.model.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long> {
    List<WorkoutSession> findByUserId(Long userId);
    java.util.Optional<WorkoutSession> findByUserIdAndDate(Long userId, java.time.LocalDate date);
}