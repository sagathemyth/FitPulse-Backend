package com.example.fitness_workout.controller;

import com.example.fitness_workout.dto.CreateGoalRequest;
import com.example.fitness_workout.dto.GoalResponse;
import com.example.fitness_workout.service.GoalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalController {
    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping
    public ResponseEntity<GoalResponse> createGoal(@Valid @RequestBody CreateGoalRequest request) {
        return ResponseEntity.ok(goalService.createGoal(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<GoalResponse>> getGoals(@PathVariable Long userId) {
        return ResponseEntity.ok(goalService.getGoals(userId));
    }

    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long goalId) {
        goalService.deleteGoal(goalId);
        return ResponseEntity.noContent().build();
    }
}
