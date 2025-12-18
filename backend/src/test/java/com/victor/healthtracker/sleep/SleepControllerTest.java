package com.victor.healthtracker.sleep;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(SleepController.class)
public class SleepControllerTest {

    @Autowired
    private MockMvc mockMvc; //simulam cereri HTTP

    @MockBean
    private SleepRepository sleepRepository; //Creez un Repository fals

    @Autowired
    private ObjectMapper objectMapper; //Pentru convertit obiecte in JSON

    @Test
    public void shouldReturnAllSleepSessions() throws Exception {
        SleepSession session1 = new SleepSession(LocalDateTime.now(), LocalDateTime.now().plusHours(8));
        List<SleepSession> sessions = Arrays.asList(session1);
        when(sleepRepository.findAll()).thenReturn(sessions);

        mockMvc.perform(get("/api/sleep"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void shouldSaveSleepSession() throws Exception {
        SleepSession newSession = new SleepSession(LocalDateTime.now(), LocalDateTime.now().plusHours(7));

        mockMvc.perform(post("/api/sleep")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSession))) //Trimitem JSON
                        .andExpect(status().isOk());
    }
}