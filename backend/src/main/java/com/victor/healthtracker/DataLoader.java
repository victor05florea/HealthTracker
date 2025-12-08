package com.victor.healthtracker;

import com.victor.healthtracker.sleep.SleepRepository;
import com.victor.healthtracker.sleep.SleepSession;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component // Ii spune serverului: "Ruleaza-ma la start!"
public class DataLoader implements CommandLineRunner {

    private final SleepRepository sleepRepository;

    public DataLoader(SleepRepository sleepRepository) {
        this.sleepRepository = sleepRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificam daca baza de date e goala
        if (sleepRepository.count() == 0) {
            System.out.println("--- Se adauga date de test pentru Somn ---");

            // Somn 1: Aseara (8 ore)
            LocalDateTime culcare1 = LocalDateTime.now().minusDays(1).withHour(23).withMinute(0);
            LocalDateTime trezire1 = LocalDateTime.now().minusDays(1).withHour(7).withMinute(0);
            sleepRepository.save(new SleepSession(culcare1, trezire1));

            // Somn 2: Alaltaieri (6 ore)
            LocalDateTime culcare2 = LocalDateTime.now().minusDays(2).withHour(0).withMinute(30);
            LocalDateTime trezire2 = LocalDateTime.now().minusDays(2).withHour(6).withMinute(30);
            sleepRepository.save(new SleepSession(culcare2, trezire2));

            System.out.println("--- Datele au fost salvate! ---");
        }
    }
}