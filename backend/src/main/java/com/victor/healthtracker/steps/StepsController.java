package com.victor.healthtracker.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/steps")
@CrossOrigin("*")
public class StepsController {

    @Autowired
    private StepsRepository stepsRepository;

    // 1. GET: Cati pasi am facut azi?
    @GetMapping("/today")
    public DailySteps getTodaySteps() {
        LocalDate today = LocalDate.now();
        // Daca nu exista, returnam 0 pasi
        return stepsRepository.findByDate(today)
                .orElse(new DailySteps(today, 0));
    }

    // 2. POST: Actualizeaza pasii de azi
    @PostMapping
    public DailySteps updateSteps(@RequestBody int stepsCount) {
        LocalDate today = LocalDate.now();

        // Cautam daca avem deja o inregistrare pe azi
        DailySteps dailySteps = stepsRepository.findByDate(today)
                .orElse(new DailySteps(today, 0));

        // Actualizam numarul
        dailySteps.setSteps(stepsCount);

        return stepsRepository.save(dailySteps);
    }
}