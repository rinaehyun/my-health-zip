package com.myhealthzip.backend.user.controller;

import com.myhealthzip.backend.user.model.User;
import com.myhealthzip.backend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public Clock clock() {
            return Clock.fixed(Instant.parse("2024-11-19T10:00:00Z"), ZoneId.of("UTC"));
        }
    }

    @Test
    @DirtiesContext
    void getAllUsersTest_whenDBIsEmpty_thenReturnEmptyList() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/api/user"))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

    @Test
    @DirtiesContext
    void getAllUsersTest_whenDBHasData_thenReturnListOfUser() throws Exception {
        // GIVEN
        Instant createdTime1 = Instant.parse("2024-11-19T10:00:00Z");
        Instant createdTime2 = Instant.parse("2024-11-23T12:30:00Z");
        userRepository.save(new User(1, "user1", "user1", createdTime1));
        userRepository.save(new User(2, "user2", "user2", createdTime2));

        // WHEN
        mockMvc.perform(get("/api/user"))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    [
                        {
                            "userId": 1,
                            "username": "user1",
                            "password": "user1",
                            "createdTime": "2024-11-19T10:00:00Z"
                        },
                        {
                            "userId": 2,
                            "username": "user2",
                            "password": "user2",
                            "createdTime": "2024-11-23T12:30:00Z"
                        }
                    ]
                """));
    }

    @Test
    @DirtiesContext
    void createAUserTest_whenPayloadIsCorrect_thenSaveUserEntity() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "username": "user1",
                        "password": "user1"
                    }
                """))
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.username").value("user1"))
                .andExpect(jsonPath("$.password").value("user1"))
                .andExpect(jsonPath("$.createdTime").value("2024-11-19T10:00:00Z"));
    }

    @Test
    void createAUserTest_whenPayloadIsNotCorrect_thenSaveUserEntity() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "username": "user1"
                    }
                """))
                // THEN
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("""
                    {
                        "status": 500,
                        "message": "The username and password cannot be null."
                    }
                """))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
