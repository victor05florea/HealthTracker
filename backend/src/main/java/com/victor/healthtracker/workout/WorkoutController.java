package com.victor.healthtracker.workout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller-ul principal pentru gestionarea antrenamentelor.
 * <p>
 * Aici sunt definite punctele de acces prin care aplicatia de mobil/web
 * trimite date catre server pentru a crea, citi sau sterge antrenamente.
 */
@RestController
@RequestMapping("/api/workouts") //toate rutele de aici incep cu /api/workouts
@CrossOrigin("*") //Permitem request-uri de pe orice IP
public class WorkoutController {

    /**
     * Avem nevoie de Repository pentru a conecta baza de date.
     */
    @Autowired
    private WorkoutRepository workoutRepository;

    /**
     * Functie ce returneaza toate antrenamentele din baza de date.
     * @return O lista JSON cu toate obiectele Workout.
     */
    @GetMapping
    public List<Workout> getAllWorkouts() {
        //Le returnam asa cum sunt
        //Apeleaza un "SELECT * FROM workout" in spate.
        return workoutRepository.findAll();
    }

    /**
     *Functie ce creeaza un antrenament nou.
     * <p>
     * Body: JSON cu datele antrenamentului (ex: {"type": "Piept"}).
     *
     * @return Obiectul salvat.
     */
    @PostMapping
    public Workout addWorkout(@RequestBody Workout workout) {
        //Validare simpla: Daca nu trimitem data de pe telefon, o punem noi automat pe cea curenta
        //Astfel evitam erori de NullPointerException mai tarziu.
        if (workout.getDate() == null) {
            workout.setDate(LocalDateTime.now());
        }
        //Salvam in baza de date
        return workoutRepository.save(workout);
    }

    /**
     * Functie care se ocupa de stergerea unui antrenament dupa ID.
     * <p>
     * Important: Deoarece am pus CascadeType.ALL in entitatea Workout,
     * stergerea antrenamentului va sterge automat si toate exercitiile asociate lui.
     *
     * @param id ID-ul antrenamentului de sters.
     */
    @DeleteMapping("/{id}")
    public void deleteWorkout(@PathVariable Long id) {
        workoutRepository.deleteById(id);
    }

    /**
     * Functie ce adauga un exercitiu specific unui antrenament existent.
     * <p>
     * Aceasta metoda face legatura intre cele doua tabele.
     *
     * @param id ID-ul antrenamentului (parinte).
     * @param exercise Datele exercitiului (copil).
     * @return Exercitiul salvat.
     */
    @PostMapping("/{id}/exercises")
    public Exercise addExerciseToWorkout(@PathVariable Long id, @RequestBody Exercise exercise) {
        //Cautam antrenamentul parinte.Daca nu exista, aruncam eroare.
        Workout workout = workoutRepository.findById(id).orElseThrow();
        //Ii spunem exercitiului cine e tatal lui
        exercise.setWorkout(workout);
        //Ii spunem tatalui ca are un nou copil
        workout.getExercises().add(exercise);
        //Salvam
        workoutRepository.save(workout);

        return exercise;
    }
}