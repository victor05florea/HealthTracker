package com.victor.healthtracker.steps;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class DailyStepsTest {

    @Test
    public void testDailyStepsEntity() {
        //Testam constructorul si getterii
        LocalDate now = LocalDate.now();
        DailySteps steps = new DailySteps(now, 10000);

        assertEquals(now, steps.getDate(), "Data ar trebui sa coincida");
        assertEquals(10000, steps.getSteps(), "Numarul de pasi ar trebui sa fie 10000");

        //Testam setterii
        steps.setSteps(15000);
        assertEquals(15000, steps.getSteps(), "Setter-ul ar trebui sa actualizeze pasii");
    }

    @Test
    public void testDefaultConstructor() {
        //Testam constructorul gol (necesar pentru Hibernate)
        DailySteps steps = new DailySteps();
        assertNull(steps.getDate(), "Data ar trebui sa fie null initial");
        assertEquals(0, steps.getSteps(), "Pasii ar trebui sa fie 0 initial");

        //Il populam ulterior
        steps.setDate(LocalDate.now());
        steps.setSteps(500);
        assertNotNull(steps.getDate());
        assertEquals(500, steps.getSteps());
    }

    @Test
    public void testNegativeSteps() {
        //Testam un caz extrem: ce se intampla daca primim pasi cu minus (eroare de senzor)
        //Clasa ar trebui sa stocheze valoarea, chiar daca e ciudata logica
        DailySteps steps = new DailySteps(LocalDate.now(), -100);
        assertEquals(-100, steps.getSteps(), "Entitatea trebuie sa accepte orice int, chiar si negativ");
    }
}