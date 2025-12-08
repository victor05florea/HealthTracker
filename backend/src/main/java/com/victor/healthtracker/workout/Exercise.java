package com.victor.healthtracker.workout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;    // Ex: "Impins la piept"
    private int sets;       // Serii
    private int reps;       // Repetari
    private double weight;  // Greutate (kg)

    @ManyToOne // Mai multe exercitii apartin unui singur antrenament
    @JoinColumn(name = "workout_id")
    @JsonIgnore // IMPORTANT: Evita bucla infinita cand trimitem datele
    private Workout workout;

    public Exercise() {}

    public Exercise(String name, int sets, int reps, double weight, Workout workout) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.workout = workout;
    }

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }
    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public Workout getWorkout() { return workout; }
    public void setWorkout(Workout workout) { this.workout = workout; }
}