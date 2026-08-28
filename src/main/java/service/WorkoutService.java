package com.example.fitness_workout.service;

import com.example.fitness_workout.dto.*;
import java.time.LocalDate;
import com.example.fitness_workout.model.*;
import com.example.fitness_workout.repository.AppUserRepository;
import com.example.fitness_workout.repository.WorkoutSessionRepository;
import org.springframework.stereotype.Service;
import com.example.fitness_workout.repository.ExerciseRepository;
import java.util.LinkedHashMap;
import java.util.Map;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutService {
    private final WorkoutSessionRepository sessionRepository;
    private final AppUserRepository userRepository;
    private final ExerciseRepository exerciseRepository;

    public WorkoutService(WorkoutSessionRepository sessionRepository, AppUserRepository userRepository, ExerciseRepository exerciseRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public ExerciseResponse logWorkout(LogWorkoutRequest request) {
        AppUser user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Find today's session for this user, or create a new one
        WorkoutSession session = sessionRepository.findByUserIdAndDate(request.getUserId(), request.getDate())
                .orElseGet(() -> {
                    WorkoutSession newSession = new WorkoutSession();
                    newSession.setUser(user);
                    newSession.setDate(request.getDate());
                    newSession.setTitle(request.getSessionTitle() != null ? request.getSessionTitle() : "Workout");
                    return sessionRepository.save(newSession);
                });

        // Build the correct polymorphic subclass based on type
        Exercise exercise;
        if ("CARDIO".equalsIgnoreCase(request.getExerciseType())) {
            CardioExercise cardio = new CardioExercise();
            cardio.setDurationMinutes(request.getDurationMinutes() != null ? request.getDurationMinutes() : 0);
            cardio.setDistanceKm(request.getDistanceKm() != null ? request.getDistanceKm() : 0);
            exercise = cardio;
        } else if ("STRENGTH".equalsIgnoreCase(request.getExerciseType())) {
            StrengthExercise strength = new StrengthExercise();
            strength.setSets(request.getSets() != null ? request.getSets() : 0);
            strength.setReps(request.getReps() != null ? request.getReps() : 0);
            strength.setWeightKg(request.getWeightKg() != null ? request.getWeightKg() : 0);
            exercise = strength;
        } else {
            throw new IllegalArgumentException("Exercise type must be CARDIO or STRENGTH");
        }

        exercise.setName(request.getExerciseName());
        exercise.setDate(request.getDate());
        exercise.setNotes(request.getNotes());
        exercise.setWorkoutSession(session);

        Exercise savedExercise = exerciseRepository.save(exercise);

        return toExerciseResponse(savedExercise);
    }

    public List<WorkoutSessionSummaryResponse> getHistory(Long userId) {
        return sessionRepository.findByUserId(userId).stream()
                .map(s -> new WorkoutSessionSummaryResponse(
                        s.getId(), s.getTitle(), s.getDate(), s.totalCalories(),
                        s.getExercises() != null ? s.getExercises().size() : 0))
                .collect(Collectors.toList());
    }

    public WorkoutSessionDetailResponse getSessionDetail(Long sessionId) {
        WorkoutSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        List<ExerciseResponse> exerciseResponses = session.getExercises().stream()
                .map(this::toExerciseResponse)
                .collect(Collectors.toList());

        return new WorkoutSessionDetailResponse(
                session.getId(), session.getTitle(), session.getDate(),
                session.totalCalories(), exerciseResponses);
    }

    public void deleteSession(Long sessionId) {
        WorkoutSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));
        // cascade = CascadeType.ALL + orphanRemoval on WorkoutSession.exercises
        // means this also deletes every Exercise that belongs to the session.
        sessionRepository.delete(session);
    }

    public ProgressResponse getProgress(Long userId) {
        List<WorkoutSession> sessions = sessionRepository.findByUserId(userId);

        LocalDate weekStart = LocalDate.now().minusDays(6);
        List<WorkoutSession> thisWeek = sessions.stream()
                .filter(s -> !s.getDate().isBefore(weekStart))
                .collect(Collectors.toList());

        double caloriesThisWeek = thisWeek.stream().mapToDouble(WorkoutSession::totalCalories).sum();

        // Build a 7-day breakdown for the bar chart, oldest to newest
        Map<LocalDate, Double> dailyCalories = new LinkedHashMap<>();
        for (int i = 6; i >= 0; i--) {
            dailyCalories.put(LocalDate.now().minusDays(i), 0.0);
        }
        for (WorkoutSession s : thisWeek) {
            dailyCalories.merge(s.getDate(), s.totalCalories(), Double::sum);
        }

        return new ProgressResponse(sessions.size(), caloriesThisWeek, dailyCalories);
    }

    private ExerciseResponse toExerciseResponse(Exercise e) {
        String type;
        Integer duration = null; Double distance = null;
        Integer sets = null; Integer reps = null; Double weight = null;

        if (e instanceof CardioExercise c) {
            type = "CARDIO";
            duration = c.getDurationMinutes();
            distance = c.getDistanceKm();
        } else if (e instanceof StrengthExercise s) {
            type = "STRENGTH";
            sets = s.getSets();
            reps = s.getReps();
            weight = s.getWeightKg();
        } else {
            type = "UNKNOWN";
        }

        return new ExerciseResponse(e.getId(), e.getName(), type, e.getDate(), e.getNotes(),
                e.caloriesBurned(), duration, distance, sets, reps, weight);
    }
}