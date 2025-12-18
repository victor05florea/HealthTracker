package com.victor.healthtracker.sleep;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

/**
 * Reprezintă o sesiune de somn a utilizatorului.
 *
 * @author Florea Victor
 * @version 1.0
 */
@Entity
public class SleepSession {

    /**
     * Identificatorul unic al sesiunii de somn.
     * Este generat automat de baza de date.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Se autoincrementeaza (1, 2, 3...)
    private Long id;


    /**
     * Momentul în care utilizatorul a adormit.
     */
    private LocalDateTime startTime;

    /**
     * Momentul în care utilizatorul s-a trezit.
     */
    private LocalDateTime endTime;

    /**
     * Constructor implicit.
     */    public SleepSession() {
    }

    /**
     * Constructor complet pentru crearea unei noi sesiuni.
     *
     * @param startTime Data și ora culcării.
     * @param endTime Data și ora trezirii.
     */
    public SleepSession(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Getteri si Setteri.
     */
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