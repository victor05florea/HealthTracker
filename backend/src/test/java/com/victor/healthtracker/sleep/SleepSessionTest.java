package com.victor.healthtracker.sleep;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class SleepSessionTest {

    @Test
    public void testSleepSessionEntity() {
        //Simulam un somn de 8 ore
        LocalDateTime culcare = LocalDateTime.of(2025, 12, 18, 23, 0);
        LocalDateTime trezire = LocalDateTime.of(2025, 12, 19, 7, 0);

        //Cream obiectul
        SleepSession session = new SleepSession(culcare, trezire);

        //Verificam daca datele au fost stocate corect
        assertEquals(culcare, session.getStartTime(), "Ora de culcare nu corespunde");
        assertEquals(trezire, session.getEndTime(), "Ora de trezire nu corespunde");
    }

    @Test
    public void testModifySessionTimes() {
        //Testam scenariul in care utilizatorul a gresit ora si o editeaza
        SleepSession session = new SleepSession();

        LocalDateTime startInitial = LocalDateTime.now();
        session.setStartTime(startInitial);
        assertEquals(startInitial, session.getStartTime());

        //Modificam ora
        LocalDateTime startNou = startInitial.minusHours(1);
        session.setStartTime(startNou);

        assertNotEquals(startInitial, session.getStartTime());
        assertEquals(startNou, session.getStartTime(), "Ora de start trebuia actualizata");
    }
}