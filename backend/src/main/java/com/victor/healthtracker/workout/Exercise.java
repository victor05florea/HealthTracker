package com.victor.healthtracker.workout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * Aici am incercat sa definesc un exercitiu specific din cadrul unui antrenament.
 * <p>
 * Aceasta clasa este tabela "copil" in relatia cu Workout.
 * De exemplu: In antrenamentul de "Piept" (Workout), avem exercitiul "Impins cu haltera" (Exercise).
 */
@Entity
public class Exercise {

    /**
     * ID-ul unic al exercitiului. Generat automat de baza de date.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Datele exercitiului
    private String name;    //Ex:"Impins la piept"
    private int sets;       //Nr de serii efectuate
    private int reps;       //Nr de repetari pe serie
    private double weight;  //Greutatea folosita (ex 12.5 kg)

    /**
     * Relatia cu antrenamentul parinte.
     * Un antrenament (Workout) are mai multe exercitii, deci aici avem @ManyToOne.
     * <p>
     * Specifica faptul ca in tabela 'Exercise' vom avea o coloana 'workout_id' (Foreign Key).
     * Fara el, cand cerem un Workout, el arata exercitiile,
     * care arata Workout-ul, care arata iar exercitiile...pana la pucla infinita.
     */
    @ManyToOne
    @JoinColumn(name="workout_id")
    @JsonIgnore
    private Workout workout;

    /**
     * Constructor gol.
     */
    public Exercise() {}

    /**
     * Constructor cu date.
     *
     * @param name Numele exercitiului.
     * @param sets Nr de serii.
     * @param reps Nr de repetari.
     * @param weight Greutatea.
     * @param workout Antrenamentul din care face parte.
     */
    public Exercise(String name,int sets,int reps,double weight,Workout workout) {
        this.name=name;
        this.sets=sets;
        this.reps=reps;
        this.weight=weight;
        this.workout=workout;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name=name;
    }
    public int getSets() {
        return sets;
    }
    public void setSets(int sets) {
        this.sets=sets;
    }
    public int getReps() {
        return reps;
    }
    public void setReps(int reps) {
        this.reps=reps;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight=weight;
    }
    public Workout getWorkout() {
        return workout;
    }
    public void setWorkout(Workout workout) {
        this.workout=workout;
    }
}