package com.victor.healthtracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller care actioneaza ca un intermediar intre aplicatia noastra si ChatGPT.
 * <p>
 * Motivul pentru care nu apelam OpenAI direct din telefon (Frontend) este securitatea:
 * Nu vrem sa expunem cheia API (apiKey) in codul aplicatiei mobile.
 */
@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
public class AiController {

    /**
     * Injectam cheia API din fisierul application.properties.
     * Spring cauta valoarea asociata cu 'openai.api.key'.
     */
    @Value("${openai.api.key}")
    private String apiKey;

    /**
     * Modelul folosit.
     */
    @Value("${openai.model}")
    private String model;

    /**
     * RestTemplate este clasa din Spring cu care putem face cereri HTTP catre alte servere (OpenAI).
     */
    private final RestTemplate restTemplate=new RestTemplate();

    /**
     * Endpoint-ul principal pentru chat.
     * Primeste mesajul utilizatorului, il trimite la OpenAI si returneaza raspunsul.
     *
     * @param userMessage Un Map care contine mesajul utilizatorului (ex: {"message": "Vreau un antrenament"}).
     * @return Un Map cu raspunsul AI-ului.
     */
    @PostMapping
    public Map<String,String> chat(@RequestBody Map<String, String> userMessage) {
        String messageContent=userMessage.get("message");

        //DEBUG
        System.out.println("Mesaj de la telefon: "+messageContent);
        //Afisam doar primele 5 caractere din cheie pentru verificare si siguranta
        System.out.println("Folosesc cheia API: "+apiKey.substring(0,5) + "...");

        //URL-ul oficial al API-ului OpenAI
        String url="https://api.openai.com/v1/chat/completions";

        //1.Construim HEADERS (antetele cererii)
        //Avem nevoie de Authorization: Bearer <KEY> si Content-Type: application/json
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer "+apiKey);

        //2.Construim BODY-ul (corpul cererii)
        Map<String,Object> body=new HashMap<>();
        body.put("model", model);

        //Lista de mesaje (istoricul conversatiei)
        List<Map<String,String>> messages=new ArrayList<>();

        //a)Ii spunem AI-ului cum sa se comporte
        Map<String,String> systemMessage=new HashMap<>();
        systemMessage.put("role","system");
        systemMessage.put("content","Ești un antrenor personal expert. Răspunde scurt în română.");
        messages.add(systemMessage);

        //b)Ce a scris user-ul in aplicatie
        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", messageContent);
        messages.add(userMsg);

        //Adaugam lista de mesaje in body
        body.put("messages", messages);

        //Impachetam totul (Headers + Body) intr-un HttpEntity
        HttpEntity<Map<String,Object>> request=new HttpEntity<>(body,headers);

        try {
            //3.Aici se face call-ul la OpenAI
            ResponseEntity<Map> response = restTemplate.postForEntity(url,request,Map.class);

            //DEBUG(Vedem daca OpenAI a raspuns cu succes)
            System.out.println("Raspuns primit de la OpenAI: "+response.getStatusCode());

            // 4.Parsare JSON
            Map<String, Object> responseBody = response.getBody();

            //OpenAI returneaza o lista de 'choices'. Noi luam prima optiune.
            List<Map<String, Object>> choices=(List<Map<String, Object>>) responseBody.get("choices");
            Map<String, Object> firstChoice=choices.get(0);

            //Din prima optiune extragem obiectul 'message' si apoi 'content'
            Map<String, String> message=(Map<String, String>) firstChoice.get("message");
            String aiReply=message.get("content");

            //Returnam doar textul simplu catre frontend
            Map<String, String> result=new HashMap<>();
            result.put("reply",aiReply);
            return result;

        } catch (HttpClientErrorException e) {
            //AICI PRINDEM EROAREA DE LA OPENAI (ex: Cheie invalida, Fonduri insuficiente)
            System.out.println("!!! EROARE OPENAI !!!");
            System.out.println("Status Code: " + e.getStatusCode());
            System.out.println("Mesaj Eroare: " + e.getResponseBodyAsString());
            Map<String, String> errorResult = new HashMap<>();
            errorResult.put("reply", "Eroare server: " + e.getStatusCode());
            return errorResult;
        } catch (Exception e) {
            // Orice alta eroare neprevazuta
            System.out.println("!!! ALTA EROARE !!!");
            e.printStackTrace();
            Map<String, String> errorResult=new HashMap<>();
            errorResult.put("reply","Eroare interna.");
            return errorResult;
        }
    }
}