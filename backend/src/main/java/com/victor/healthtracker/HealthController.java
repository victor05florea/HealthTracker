package com.victor.healthtracker;

import org.springframework.web.bind.annotation.CrossOrigin; // <-- Asigura-te ca ai importul asta
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*") // <-- Permite accesul oricui (telefonului)
public class HealthController {

    @GetMapping("/test")
    public String sayHello() {
        return "Salut! Backend-ul Java functioneaza!";
    }
}