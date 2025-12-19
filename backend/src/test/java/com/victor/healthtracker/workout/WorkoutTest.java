package com.victor.healthtracker.workout;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class WorkoutTest {

    @Test
    public void testWorkoutAndExerciseRelationship() {
        //Cream Antrenamentul
        Workout workout = new Workout("Piept + Triceps", LocalDateTime.now());

        //Cream Exercitiul
        Exercise exercise = new Exercise("Impins la banca", 4, 10, 80.5, null);

        //Facem legatura
        exercise.setWorkout(workout);
        workout.getExercises().add(exercise);

        //Verificari
        assertFalse(workout.getExercises().isEmpty(), "Lista de exercitii nu ar trebui sa fie goala");
        assertEquals(1, workout.getExercises().size(), "Ar trebui sa avem exact un exercitiu");
        assertEquals("Impins la banca", workout.getExercises().get(0).getName());
        assertEquals(workout, exercise.getWorkout(), "Exercitiul ar trebui sa fie legat de acest antrenament");
    }

    @Test
    public void testMultipleExercises() {
        //Testam un antrenament cu 2 exercitii
        Workout workout = new Workout("Picioare", LocalDateTime.now());

        Exercise ex1 = new Exercise("Genuflexiuni", 5, 5, 100.0, workout);
        Exercise ex2 = new Exercise("Presa", 4, 12, 200.0, workout);

        workout.getExercises().add(ex1);
        workout.getExercises().add(ex2);

        assertEquals(2, workout.getExercises().size(), "Trebuie sa avem 2 exercitii");
        assertEquals("Genuflexiuni", workout.getExercises().get(0).getName());
        assertEquals("Presa", workout.getExercises().get(1).getName());
    }

    @Test
    public void testRemoveExercise() {
        //Testam stergerea unui exercitiu din lista
        Workout workout = new Workout("Cardio", LocalDateTime.now());
        Exercise ex1 = new Exercise("Alergare", 1, 30, 0, workout);

        workout.getExercises().add(ex1);
        assertEquals(1, workout.getExercises().size());

        //Stergem exercitiul
        workout.getExercises().remove(ex1);
        assertEquals(0, workout.getExercises().size(), "Lista ar trebui sa fie goala dupa stergere");
    }
}