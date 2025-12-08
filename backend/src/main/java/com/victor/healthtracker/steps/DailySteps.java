package com.victor.healthtracker.steps;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class DailySteps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date; // Data (ex: 2025-12-08)
    private int steps;      // Numarul de pasi

    public DailySteps() {}

    public DailySteps(LocalDate date, int steps) {
        this.date = date;
        this.steps = steps;
    }

    // Getters si Setters
    public Long getId() { return id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public int getSteps() { return steps; }
    public void setSteps(int steps) { this.steps = steps; }
}