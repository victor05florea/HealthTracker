package com.victor.healthtracker.sleep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController  // <--- CRITIC: Fara asta, clasa nu e vazuta ca server
@RequestMapping("/api/sleep") // <--- Asta defineste prima parte a link-ului
@CrossOrigin("*")
public class SleepController {

    @Autowired
    private SleepRepository sleepRepository;

    @GetMapping // Link: /api/sleep
    public List<SleepSession> getAllSleepSessions() {
        return sleepRepository.findAll();
    }

    @PostMapping
    public SleepSession addSleepSession(@RequestBody SleepSession session) {
        return sleepRepository.save(session);
    }

    // VERIFICA ASTA:
    @GetMapping("/populate") // <--- Asta defineste a doua parte a link-ului
    public String addFakeData() {
        SleepSession s1 = new SleepSession(LocalDateTime.now().minusHours(8), LocalDateTime.now());
        SleepSession s2 = new SleepSession(LocalDateTime.now().minusDays(1).minusHours(9), LocalDateTime.now().minusDays(1).minusHours(1));

        sleepRepository.save(s1);
        sleepRepository.save(s2);
        return "Am adaugat 2 sesiuni de somn in baza de date!";
    }
}