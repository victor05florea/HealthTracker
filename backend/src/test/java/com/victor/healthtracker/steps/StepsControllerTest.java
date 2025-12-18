package com.victor.healthtracker.steps;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Clasa de test pentru StepsController.
 * <p>
 * Verificam daca endpoint-urile REST raspund corect, fara a conecta o baza de date reala.
 */
@WebMvcTest(StepsController.class) //Incarcam doar contextul pentru acest Controller
public class StepsControllerTest {

    @Autowired
    private MockMvc mockMvc; //Cu ajutorul acesteia trimitem cereri HTTP simulate

    @MockBean
    private StepsRepository stepsRepository; //Cream o dublura a repository-ului

    /**
     * Testam scenariul: GET /api/steps/today
     * Cand cerem pasii de azi, si ei exista in baza de date.
     */
    @Test
    public void shouldReturnTodaySteps() throws Exception {
        LocalDate today = LocalDate.now();
        DailySteps mockSteps = new DailySteps(today, 5000);
        when(stepsRepository.findByDate(today)).thenReturn(Optional.of(mockSteps));

        mockMvc.perform(get("/api/steps/today"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.steps").value(5000)) // Verificam ca JSON-ul are campul steps = 5000
                .andExpect(jsonPath("$.date").exists()); // Verificam ca avem o data
    }

    /**
     * Testam scenariul: POST /api/steps
     * Cand trimitem un numar de pasi pentru a fi actualizat.
     */
    @Test
    public void shouldUpdateSteps() throws Exception {
        int newStepsCount=8000;
        LocalDate today=LocalDate.now();

        //Simulam ca deja aveam 5000 de pasi
        DailySteps existingRecord=new DailySteps(today, 5000);

        //Simulam ce returneaza baza de date cand salvam
        DailySteps savedRecord=new DailySteps(today,newStepsCount);

        when(stepsRepository.findByDate(today)).thenReturn(Optional.of(existingRecord));
        when(stepsRepository.save(any(DailySteps.class))).thenReturn(savedRecord);

        mockMvc.perform(post("/api/steps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(newStepsCount)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.steps").value(8000)); //Verificam ca raspunsul contine valoarea actualizata
    }
}