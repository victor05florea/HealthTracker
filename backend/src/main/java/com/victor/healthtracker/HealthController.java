package com.victor.healthtracker;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller simplu de tip "Health Check".
 * <p>
 * Rolul acestei clase este doar sa ne confirme ca serverul a pornit cu succes
 * si ca este vizibil in retea.Nu interactioneaza cu baza de date.
 */
@RestController
@CrossOrigin("*") //Permite accesul oricui
public class HealthController {

    /**
     * Endpoint de test.
     * @return Un mesaj text simplu de confirmare.
     */
    @GetMapping("/test")
    public String sayHello() {
        return "Salut! Backend-ul Java functioneaza!";
    }
}