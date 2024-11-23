package com.myhealthzip.backend.user.controller;

import com.myhealthzip.backend.user.model.User;
import com.myhealthzip.backend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    Clock fixedClock = Clock.fixed(Instant.parse("2023-11-19T10:00:00Z"), ZoneId.of("UTC"));
    private final Instant createdTime = Instant.now(fixedClock);

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
        userRepository.save(new User(1, "user1", "user1", createdTime));
        userRepository.save(new User(2, "user2", "user2", createdTime));

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
                            "createdTime": "2023-11-19T10:00:00Z"
                        },
                        {
                            "userId": 2,
                            "username": "user2",
                            "password": "user2",
                            "createdTime": "2023-11-19T10:00:00Z"
                        }
                    ]
                """));
    }
}
