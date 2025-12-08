package com.victor.healthtracker.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StepsRepository extends JpaRepository<DailySteps, Long> {
    // Functie speciala sa gasim inregistrarea dupa data (ca sa nu cream duplicate pt aceeasi zi)
    Optional<DailySteps> findByDate(LocalDate date);
}