package com.victor.healthtracker.workout;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Aici este reprezentat un antrenament.
 * <p>
 * Aceasta este tabela "parinte". Un antrenament contine o lista de exercitii.
 * De exemplu: Antrenamentul de "Luni - Piept" (Workout) contine 3 tipuri de impins (Exercise).
 */
@Entity
public class Workout {

    /**
     * ID unic pentru antrenament.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Tipul sau numele antrenamentului.
     * Ex: "Spate + Biceps", "Cardio", "Leg Day".
     */
    private String type;

    /**
     * Data si ora la care a avut loc antrenamentul.
     */
    private LocalDateTime date;

    /**
     * Relatia One-to-Many cu exercitiile.
     * <p>
     * mappedBy="workout": Spune ca relatia este gestionata de campul 'workout' din clasa Exercise.
     * cascade=CascadeType.ALL: Daca salvam/stergem antrenamentul, se salveaza/sterg automat si exercitiile lui.
     * orphanRemoval=true: Daca scoatem un exercitiu din lista asta, el va fi sters fizic si din baza de date.
     */
    @OneToMany(mappedBy="workout",cascade=CascadeType.ALL,orphanRemoval=true)
    private List<Exercise> exercises=new ArrayList<>();

    /**
     * Constructor gol.
     */
    public Workout() {
    }

    /**
     * Constructor pentru crearea unui nou antrenament.
     * Nu includem lista de exercitii aici, o vom popula ulterior.
     *
     * @param type Tipul antrenamentului (ex:"Full Body").
     * @param date Data antrenamentului.
     */
    public Workout(String type, LocalDateTime date) {
        this.type=type;
        this.date=date;
    }

    /**
     * Getteri si Setteri
     */
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id=id;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type=type;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date=date;
    }
    /**
     * Returneaza lista de exercitii asociata acestui antrenament.
     */
    public List<Exercise> getExercises() {
        return exercises;
    }

    /**
     * Seteaza lista completa de exercitii.
     */
    public void setExercises(List<Exercise> exercises) {
        this.exercises=exercises;
    }
}