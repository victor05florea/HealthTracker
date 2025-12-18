package com.victor.healthtracker;

import com.victor.healthtracker.sleep.SleepRepository;
import com.victor.healthtracker.sleep.SleepSession;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * Clasa utilitara folosita pentru a popula baza de date cu date de test (Mock Data).
 * <p>
 * Implementand interfata CommandLineRunner, metoda run() va fi executata automat
 * de catre Spring Boot imediat dupa ce porneste serverul.
 * Este foarte utila in faza de dezvoltare ca sa nu existe tabele goale.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final SleepRepository sleepRepository;

    /**
     * Spring ne ofera instanta de SleepRepository ca sa putem salva date.
     */
    public DataLoader(SleepRepository sleepRepository) {
        this.sleepRepository = sleepRepository;
    }

    /**
     * Aceasta metoda ruleaza o singura data, la pornirea aplicatiei.
     */
    @Override
    public void run(String... args) throws Exception {
        //1.Verificam daca baza de date e goala (count == 0).
        if (sleepRepository.count()==0) {
            System.out.println("Se adauga date de test pentru Somn:");

            // 2. Cream cateva sesiuni de somn fictive

            //Somn 1: Aseara (8 ore)
            //Folosim LocalDateTime pentru a calcula data relativa la momentul actual (.minusDays(1))
            LocalDateTime culcare1=LocalDateTime.now().minusDays(1).withHour(23).withMinute(0);
            LocalDateTime trezire1=LocalDateTime.now().minusDays(1).withHour(7).withMinute(0); // Nota: aici ar fi trebuit sa fie ziua urmatoare logic, dar pentru test e ok

            //Salvam in baza de date
            sleepRepository.save(new SleepSession(culcare1, trezire1));

            //Somn 2: Alaltaieri (6 ore)
            LocalDateTime culcare2=LocalDateTime.now().minusDays(2).withHour(0).withMinute(30);
            LocalDateTime trezire2=LocalDateTime.now().minusDays(2).withHour(6).withMinute(30);

            //Salvam in baza de date

            sleepRepository.save(new SleepSession(culcare2, trezire2));

            System.out.println("--- Datele au fost salvate! ---");
        }
    }
}