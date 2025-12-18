package com.victor.healthtracker.steps;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Clasa care reprezinta entitatea pentru pasii zilnici.
 * Aceasta clasa este mapata la o tabela din baza de date folosind Hibernate.
 * Fiecare instanta a acestei clase corespunde unui rand din tabela 'DailySteps'.
 */
@Entity
public class DailySteps {

    /**
     * Identificatorul unic (Primary Key) pentru fiecare inregistrare.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Data calendaristica pentru care se inregistreaza pasii.
     */
    private LocalDate date; //2025-12-25

    /**
     * Numarul de pasi efectuati in acea zi.
     */
    private int steps;

    /**
     * Constructor gol
     */
    public DailySteps() {
    }

    /**
     * Constructor cu parametri.
     * <p>
     * Folosit atunci cand vrem sa cream un obiect nou DailySteps in aplicatie
     *
     * @param date  Data inregistrarii.
     * @param steps Numarul de pasi.
     */
    public DailySteps(LocalDate date, int steps) {
        this.date = date;
        this.steps = steps;
    }


    /**
     * Returneaza ID-ul inregistrarii.
     * @return id-ul unic.
     */
    public Long getId() {
        return id;
    }

    /**
     * Returneaza data inregistrarii.
     * @return data sub forma de LocalDate.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Seteaza data inregistrarii.
     * @param date noua data.
     */
    public void setDate(LocalDate date) {
        this.date=date;
    }

    /**
     * Returneaza numarul de pasi.
     * @return numarul de pasi.
     */
    public int getSteps() {
        return steps;
    }

    /**
     * Actualizeaza numarul de pasi.
     * @param steps noul numar de pasi.
     */
    public void setSteps(int steps) {
        this.steps=steps;
    }
}