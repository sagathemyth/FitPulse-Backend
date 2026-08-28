package com.example.fitness_workout.service;

import com.example.fitness_workout.dto.CreateGoalRequest;
import com.example.fitness_workout.dto.GoalResponse;
import com.example.fitness_workout.model.AppUser;
import com.example.fitness_workout.model.Goal;
import com.example.fitness_workout.model.WorkoutSession;
import com.example.fitness_workout.repository.AppUserRepository;
import com.example.fitness_workout.repository.GoalRepository;
import com.example.fitness_workout.repository.WorkoutSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GoalService {

    // The only goal types we know how to auto-calculate progress for.
    private static final Set<String> VALID_TYPES = Set.of(
            "CALORIES_WEEK", "CALORIES_MONTH", "WORKOUTS_WEEK", "WORKOUTS_MONTH"
    );

    private final GoalRepository goalRepository;
    private final AppUserRepository userRepository;
    private final WorkoutSessionRepository sessionRepository;

    public GoalService(GoalRepository goalRepository, AppUserRepository userRepository, WorkoutSessionRepository sessionRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public GoalResponse createGoal(CreateGoalRequest request) {
        if (!VALID_TYPES.contains(request.getType())) {
            throw new IllegalArgumentException("Goal type must be one of: " + VALID_TYPES);
        }

        AppUser user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Goal goal = new Goal();
        goal.setUser(user);
        goal.setType(request.getType());
        goal.setTargetValue(request.getTargetValue());
        goal.setCurrentValue(0); // unused now — real progress is always computed live

        Goal saved = goalRepository.save(goal);
        return toResponse(saved, List.of());
    }

    public List<GoalResponse> getGoals(Long userId) {
        List<WorkoutSession> sessions = sessionRepository.findByUserId(userId);
        return goalRepository.findByUserId(userId).stream()
                .map(g -> toResponse(g, sessions))
                .collect(Collectors.toList());
    }

    public void deleteGoal(Long goalId) {
        if (!goalRepository.existsById(goalId)) {
            throw new IllegalArgumentException("Goal not found");
        }
        goalRepository.deleteById(goalId);
    }

    private GoalResponse toResponse(Goal goal, List<WorkoutSession> sessions) {
        double current = computeCurrentValue(goal, sessions);
        boolean achieved = current >= goal.getTargetValue();
        return new GoalResponse(goal.getId(), goal.getType(), goal.getTargetValue(), current, achieved);
    }

    /**
     * Computes live progress for a goal from the user's actual logged workout sessions —
     * no manual entry. Unrecognized goal types (e.g. leftover free-text goals from before
     * this feature was auto-tracked) safely fall back to 0 instead of failing.
     */
    private double computeCurrentValue(Goal goal, List<WorkoutSession> sessions) {
        String type = goal.getType();
        if (!VALID_TYPES.contains(type)) {
            return 0;
        }

        LocalDate today = LocalDate.now();
        boolean isWeekly = type.endsWith("_WEEK");
        LocalDate rangeStart = isWeekly ? today.minusDays(6) : today.withDayOfMonth(1);

        List<WorkoutSession> inRange = sessions.stream()
                .filter(s -> !s.getDate().isBefore(rangeStart))
                .collect(Collectors.toList());

        boolean isCalorieGoal = type.startsWith("CALORIES");
        if (isCalorieGoal) {
            return inRange.stream().mapToDouble(WorkoutSession::totalCalories).sum();
        } else {
            return inRange.size(); // "workouts" = number of sessions logged in range
        }
    }
}
