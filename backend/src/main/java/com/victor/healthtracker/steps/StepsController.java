package com.victor.healthtracker.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

/**
 * Controller REST care se ocupă de cererile legate de pași.
 * <p>
 * Prin această clasă, aplicația de frontend poate
 * citi și salva numărul de pași ai utilizatorului.
 */
@RestController
@RequestMapping("/api/steps") //Toate rutele din aceasta clasa vor incepe cu /api/steps
@CrossOrigin("*") //Permitem accesul de pe orice domeniu
public class StepsController {

    /**
     * Repository-ul ne ajută să lucrăm cu datele din baza de date.
     * Spring îl creează automat și ni-l oferă pentru utilizare.
     */
    @Autowired
    private StepsRepository stepsRepository;

    /**
     * Endpoint GET care returnează numărul de pași pentru ziua de azi.
     * <p>
     * URL: GET /api/steps/today
     *
     * @return obiectul DailySteps pentru ziua curentă.
     * Dacă nu există date salvate, se returnează un obiect nou cu 0 pași.
     */
    @GetMapping("/today")
    public DailySteps getTodaySteps() {
        //Preiau data curenta
        LocalDate today = LocalDate.now();

        //Căutăm în baza de date pașii pentru ziua de azi.
        //Dacă nu există nimic, returnăm un obiect nou cu 0 pași
        return stepsRepository.findByDate(today)
                .orElse(new DailySteps(today, 0));
    }

    /**
     * Endpoint POST care salvează sau actualizează numărul de pași pentru ziua curentă.
     * <p>
     * URL: POST /api/steps
     * Body: un număr întreg care reprezintă totalul de pași.
     *
     * @param stepsCount numărul de pași primit din request.
     * @return obiectul DailySteps salvat în baza de date.
     */
    @PostMapping
    public DailySteps updateSteps(@RequestBody int stepsCount) {
        //Preiau data de azi
        LocalDate today = LocalDate.now();
        //Verificăm dacă există deja o înregistrare pentru ziua de azi.
        //Daca exista, o luam pe aceea.Daca nu, cream una noua cu 0 pasi.
        DailySteps dailySteps = stepsRepository.findByDate(today)
                .orElse(new DailySteps(today, 0));
        //Actualizam nr de pasi
        dailySteps.setSteps(stepsCount);
        //Salvam modificarea inapoi in baza de date
        return stepsRepository.save(dailySteps);
    }
}