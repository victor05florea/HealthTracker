package com.victor.healthtracker.workout;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WorkoutRepositoryTest {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Test
    public void shouldSaveAndFindWorkout() {
        //Salvam un antrenament in baza de date
        Workout workout = new Workout("Full-Body", LocalDateTime.now());
        workoutRepository.save(workout);

        //Il cautam
        List<Workout> workouts = workoutRepository.findAll();

        //Verificam ca a fost gasit
        assertThat(workouts).isNotEmpty();
        assertThat(workouts.get(0).getType()).isEqualTo("Full-Body");
    }
}