package com.myhealthzip.backend.bloodpressure.controller;

import com.myhealthzip.backend.bloodpressure.model.BloodPressure;
import com.myhealthzip.backend.bloodpressure.repository.BloodPressureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BloodPressureIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BloodPressureRepository bloodPressureRepository;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public Clock clock() {
            return Clock.fixed(Instant.parse("2024-12-10T10:00:00Z"), ZoneId.of("UTC"));
        }
    }

    @Test
    void getBloodPressuresTest_whenDBIsEmpty_thenReturnEmptyList() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/api/blood-pressure"))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getBloodPressuresTest_whenDBHasData_thenReturnListOfBloodPressure() throws Exception {
        // GIVEN
        Instant createdTime1 = Instant.parse("2024-12-04T10:00:00Z");
        Instant createdTime2 = Instant.parse("2024-12-05T12:30:00Z");
        bloodPressureRepository.save(new BloodPressure(1, 1, 120, 80, createdTime1));
        bloodPressureRepository.save(new BloodPressure(2, 1, 130, 75, createdTime2));

        // WHEN
        mockMvc.perform(get("/api/blood-pressure"))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    [
                        {
                            "bloodPressureId": 1,
                            "userId": 1,
                            "systolic": 120,
                            "diastolic": 80,
                            "createdTime": "2024-12-04T10:00:00Z"
                        },
                        {
                            "bloodPressureId": 2,
                            "userId": 1,
                            "systolic": 130,
                            "diastolic": 75,
                            "createdTime": "2024-12-05T12:30:00Z"
                        }
                    ]
                """));
    }

    @Test
    void saveBloodPressureTest_whenPayloadIsCorrect_thenReturnBloodPressureEntity() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/api/blood-pressure")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "systolic": 120,
                                "diastolic": 80
                            }
                        """)
                )
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bloodPressureId").exists())
                .andExpect(jsonPath("$.userId").doesNotExist())
                .andExpect(jsonPath("$.systolic").value(120))
                .andExpect(jsonPath("$.diastolic").value(80))
                .andExpect(jsonPath("$.createdTime").value("2024-12-10T10:00:00Z"));
    }
}
