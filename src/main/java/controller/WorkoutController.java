package com.example.fitness_workout.controller;

import com.example.fitness_workout.dto.*;
import com.example.fitness_workout.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping("/log")
    public ResponseEntity<ExerciseResponse> logWorkout(@Valid @RequestBody LogWorkoutRequest request) {
        return ResponseEntity.ok(workoutService.logWorkout(request));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<WorkoutSessionSummaryResponse>> getHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(workoutService.getHistory(userId));
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<WorkoutSessionDetailResponse> getSessionDetail(@PathVariable Long sessionId) {
        return ResponseEntity.ok(workoutService.getSessionDetail(sessionId));
    }

    @DeleteMapping("/session/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long sessionId) {
        workoutService.deleteSession(sessionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/progress/{userId}")
    public ResponseEntity<ProgressResponse> getProgress(@PathVariable Long userId) {
        return ResponseEntity.ok(workoutService.getProgress(userId));
    }
}