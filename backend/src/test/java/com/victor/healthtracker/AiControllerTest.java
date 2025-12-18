package com.victor.healthtracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AiController.class)
// IMPORTAM clasa principala pentru a prelua metoda @Bean restTemplate()
@Import(HealthtrackerApplication.class)
public class AiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    //Folosim RestTemplate-ul REAL configurat in aplicatie
    @Autowired
    private RestTemplate restTemplate;

    //Elementul care va simula serverul OpenAI
    private MockRestServiceServer mockServer;

    @BeforeEach
    public void init() {
        //Conectam serverul fals la RestTemplate-ul nostru real
        //Orice cerere facuta de restTemplate va fi interceptata de mockServer
        mockServer=MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void shouldReturnAiResponse() throws Exception {
        //Definim raspunsul JSON pe care ne asteptam sa il primim de la OpenAI
        //(Este formatul standard OpenAI simplificat)
        String openaiResponseJson = "{" +
                "\"choices\": [" +
                "  {\"message\": {\"content\": \"Salut! Sunt AI-ul tau.\"}}" +
                "]" +
                "}";

        //Programam serverul fals:
        mockServer.expect(requestTo("https://api.openai.com/v1/chat/completions"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(openaiResponseJson,MediaType.APPLICATION_JSON));

        //Trimitem mesajul de la utilizator catre controller-ul nostru
        String userMessageJson = "{\"message\": \"Vreau un sfat.\"}";

        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userMessageJson))
                        //VERIFICARE
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.reply").value("Salut! Sunt AI-ul tau."));
    }
}