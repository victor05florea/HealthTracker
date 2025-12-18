package com.victor.healthtracker.workout;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

public class ExerciseTest {
    @Test
    public void testExerciseCreation() {
        String name = "Genuflexiuni";
        int sets = 4;
        int reps = 12;
        double weight = 50.5;

        Exercise exercise = new Exercise();
        exercise.setName(name);
        exercise.setSets(sets);
        exercise.setReps(reps);
        exercise.setWeight(weight);

        //Verificam daca ce am pus e ce primim inapoi
        assertEquals("Genuflexiuni", exercise.getName());
        assertEquals(4, exercise.getSets());
        assertEquals(50.5, exercise.getWeight(),0.001);
    }
}