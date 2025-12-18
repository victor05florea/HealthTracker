package com.victor.healthtracker.workout;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Teste unitare pentru WorkoutController.
 * <p>
 * Aici verificam fluxul complet: Listare, Adaugare Antrenament, Stergere si Adaugare Exercitiu.
 */
@WebMvcTest(WorkoutController.class)
public class WorkoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkoutRepository workoutRepository;

    @Autowired
    private ObjectMapper objectMapper; //Ne ajuta sa transformam obiectele Java in JSON

    /**
     * 1. Testam GET /api/workouts
     */
    @Test
    public void shouldReturnAllWorkouts() throws Exception {
        Workout w1=new Workout("Piept", LocalDateTime.now());
        Workout w2=new Workout("Spate", LocalDateTime.now().minusDays(1));

        when(workoutRepository.findAll()).thenReturn(Arrays.asList(w1, w2));
        mockMvc.perform(get("/api/workouts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)) // Verificam ca primim 2 antrenamente
                .andExpect(jsonPath("$[0].type").value("Piept"));
    }

    /**
     * 2. Testam POST /api/workouts
     */
    @Test
    public void shouldAddWorkout() throws Exception {
        Workout newWorkout = new Workout("Picioare", null); //Trimitem fara data, controller-ul o va pune
        Workout savedWorkout = new Workout("Picioare", LocalDateTime.now());
        savedWorkout.setId(1L);

        when(workoutRepository.save(any(Workout.class))).thenReturn(savedWorkout);
        mockMvc.perform(post("/api/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newWorkout))) //Convertim obiectul in JSON string
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.type").value("Picioare"))
                        .andExpect(jsonPath("$.id").value(1));
    }

    /**
     * 3. Testam DELETE /api/workouts/{id}
     */
    @Test
    public void shouldDeleteWorkout() throws Exception {
        Long workoutId=1L;
        mockMvc.perform(delete("/api/workouts/{id}", workoutId))
                .andExpect(status().isOk());

        //Verificam daca metoda delete din repository a fost apelata exact o data
        verify(workoutRepository).deleteById(workoutId);
    }

    /**
     * 4. Testam POST /api/workouts/{id}/exercises
     * Acesta este cel mai complex test, pentru ca implica relatia intre doua entitati.
     */
    @Test
    public void shouldAddExerciseToWorkout() throws Exception {
        Long workoutId=10L;
        Workout workout=new Workout("Biceps", LocalDateTime.now());
        workout.setId(workoutId);
        Exercise newExercise=new Exercise("Flexii cu gantera", 3, 12, 10.0, null);

        //Cand controller-ul cauta antrenamentul dupa ID, il gaseste pe al nostru
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workout));
        //Cand salveaza, returnam antrenamentul actualizat (nu e critic ce returneaza save aici, ci fluxul)
        when(workoutRepository.save(any(Workout.class))).thenReturn(workout);

        mockMvc.perform(post("/api/workouts/{id}/exercises", workoutId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newExercise)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Flexii cu gantera"));
        //Verificam faptul ca exercitiul a fost adaugat in lista antrenamentului
    }
}