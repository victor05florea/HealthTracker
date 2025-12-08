package com.victor.healthtracker.sleep;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity // Asta ii spune lui Java: "Fa un tabel din clasa asta!"
public class SleepSession {

    @Id // Aceasta este cheia primara (ID unic)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Se autoincrementeaza (1, 2, 3...)
    private Long id;

    private LocalDateTime startTime; // Cand te-ai culcat
    private LocalDateTime endTime;   // Cand te-ai trezit

    // Constructor gol (Obligatoriu pentru Spring)
    public SleepSession() {
    }

    // Constructor cu date
    public SleepSession(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // --- Getters si Setters (Ca sa putem accesa datele) ---
    // Poti sa le generezi automat cu Alt+Insert, sau sa le copiezi de aici:

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}