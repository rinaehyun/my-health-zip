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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @DirtiesContext
    void getBloodPressuresTest_whenDBIsEmpty_thenReturnEmptyList() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/api/blood-pressure"))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
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
    @DirtiesContext
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

    @Test
    @DirtiesContext
    void saveBloodPressureTest_whenPayloadIsNotCorrect_thenThrow() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/api/blood-pressure")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "systolic": 120,
                                "diastolic": null
                            }
                        """)
        )
                // THEN
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("""
                    {
                        "status": 500,
                        "message": "The input of blood pressure is not completed."
                    }
                """))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DirtiesContext
    void deleteBloodPressureByIdTest_whenIdExists_thenDeleteBloodPressureEntity() throws Exception {
        // GIVEN
        Instant createdTime1 = Instant.parse("2024-12-04T10:00:00Z");
        Instant createdTime2 = Instant.parse("2024-12-05T12:30:00Z");
        bloodPressureRepository.save(new BloodPressure(1, 1, 120, 80, createdTime1));
        bloodPressureRepository.save(new BloodPressure(2, 1, 130, 75, createdTime2));

        mockMvc.perform(get("/api/blood-pressure"))
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

        // WHEN
        mockMvc.perform(delete("/api/blood-pressure/1"))
                // THEN
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/blood-pressure"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    [
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
    @DirtiesContext
    void updateBloodPressureByIdTest_whenIdExists_thenUpdateBPEntity() throws Exception {
        // GIVEN
        Instant createdTime1 = Instant.parse("2024-12-04T10:00:00Z");
        bloodPressureRepository.save(new BloodPressure(1, 1, 130, 75, createdTime1));

        // WHEN
        mockMvc.perform(put("/api/blood-pressure/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "systolic": 125,
                                "diastolic": 90
                            }
                            """)
                )
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {
                        "bloodPressureId": 1,
                        "userId": 1,
                        "systolic": 125,
                        "diastolic": 90,
                        "createdTime": "2024-12-04T10:00:00Z"
                    }
            """));
    }

    @Test
    @DirtiesContext
    void updateBloodPressureByIdTest_whenIdDoesNotExist_thenThrow() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(put("/api/blood-pressure/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "systolic": 125,
                                "diastolic": 90
                            }
                            """)
                )
                // THEN
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                {
                    "status": 404,
                    "message": "Blood Pressure with id 1 cannot be found."
                }
            """))
                .andExpect(jsonPath("$.timestamp").exists());
    }

}
