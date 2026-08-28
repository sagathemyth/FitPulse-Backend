package com.example.fitness_workout.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

/**
 * Unit tests for the OOP core of the project: Exercise (abstract) and its
 * CardioExercise / StrengthExercise subclasses, plus WorkoutSession.totalCalories()
 * which relies on runtime polymorphism to sum calories across mixed exercise types.
 *
 * These are plain POJO tests — no Spring context, no database — so they run fast
 * and specifically exercise the inheritance/polymorphism design.
 */
class ExercisePolymorphismTest {

    @Test
    void cardioExercise_calculatesCaloriesFromDurationAndDistance() {
        CardioExercise cardio = new CardioExercise();
        cardio.setDurationMinutes(30);
        cardio.setDistanceKm(5);

        // (30 * 10) + (5 * 5) = 325
        assertEquals(325.0, cardio.caloriesBurned(), 0.001);
    }

    @Test
    void strengthExercise_calculatesCaloriesFromSetsRepsWeight() {
        StrengthExercise strength = new StrengthExercise();
        strength.setSets(4);
        strength.setReps(10);
        strength.setWeightKg(50);

        // 4 * 10 * (50 * 0.1) = 200
        assertEquals(200.0, strength.caloriesBurned(), 0.001);
    }

    @Test
    void exercise_polymorphicDispatch_choosesCorrectSubclassFormula() {
        // Held as the abstract supertype, exactly like WorkoutSession/WorkoutService do.
        CardioExercise cardioImpl = new CardioExercise();
        cardioImpl.setDurationMinutes(20);
        cardioImpl.setDistanceKm(0);
        Exercise cardio = cardioImpl;

        StrengthExercise strengthImpl = new StrengthExercise();
        strengthImpl.setSets(3);
        strengthImpl.setReps(8);
        strengthImpl.setWeightKg(20);
        Exercise strength = strengthImpl;

        // Calling caloriesBurned() through the Exercise reference must resolve to
        // each subclass's own override, not a shared/base implementation.
        assertEquals(200.0, cardio.caloriesBurned(), 0.001);   // 20*10 + 0*5
        assertEquals(48.0, strength.caloriesBurned(), 0.001);  // 3*8*(20*0.1)
    }

    @Test
    void workoutSession_totalCalories_sumsAcrossMixedExerciseTypes() {
        CardioExercise cardio = new CardioExercise();
        cardio.setDurationMinutes(10);
        cardio.setDistanceKm(2);
        // 10*10 + 2*5 = 110

        StrengthExercise strength = new StrengthExercise();
        strength.setSets(2);
        strength.setReps(5);
        strength.setWeightKg(30);
        // 2*5*(30*0.1) = 30

        WorkoutSession session = new WorkoutSession();
        session.setExercises(List.of(cardio, strength));

        assertEquals(140.0, session.totalCalories(), 0.001);
    }

    @Test
    void workoutSession_totalCalories_isZeroWhenNoExercisesLogged() {
        WorkoutSession session = new WorkoutSession();
        assertEquals(0.0, session.totalCalories(), 0.001);
    }
}
