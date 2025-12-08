package com.victor.healthtracker.workout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/workouts")
@CrossOrigin("*")
public class WorkoutController {

    @Autowired
    private WorkoutRepository workoutRepository;

    // 1. GET: Lista de antrenamente (Istoric)
    @GetMapping
    public List<Workout> getAllWorkouts() {
        // Le returnam asa cum sunt (ideal ar fi sortate dupa data, dar lasam simplu acum)
        return workoutRepository.findAll();
    }

    // 2. POST: Adauga un antrenament nou
    @PostMapping
    public Workout addWorkout(@RequestBody Workout workout) {
        // Daca nu trimitem data de pe telefon, o punem noi automat pe cea curenta
        if (workout.getDate() == null) {
            workout.setDate(LocalDateTime.now());
        }
        return workoutRepository.save(workout);
    }
    // 3. DELETE: Sterge un antrenament (si exercitiile lui automat)
    @DeleteMapping("/{id}")
    public void deleteWorkout(@PathVariable Long id) {
        workoutRepository.deleteById(id);
    }

    // 4. POST: Adauga un exercitiu la un antrenament specific
    @PostMapping("/{id}/exercises")
    public Exercise addExerciseToWorkout(@PathVariable Long id, @RequestBody Exercise exercise) {
        Workout workout = workoutRepository.findById(id).orElseThrow();
        exercise.setWorkout(workout);
        workout.getExercises().add(exercise);
        workoutRepository.save(workout); // Salvam parintele, se salveaza si copilul
        return exercise;
    }
}