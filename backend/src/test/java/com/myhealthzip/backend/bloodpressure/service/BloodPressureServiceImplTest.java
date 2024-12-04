package com.myhealthzip.backend.bloodpressure.service;

import com.myhealthzip.backend.bloodpressure.model.BloodPressure;
import com.myhealthzip.backend.bloodpressure.repository.BloodPressureRepository;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BloodPressureServiceImplTest {

    private final BloodPressureRepository bloodPressureRepository = mock(BloodPressureRepository.class);
    Clock fixedClock = Clock.fixed(Instant.parse("2024-12-04T15:00:00Z"), ZoneId.of("UTC"));

    BloodPressureServiceImpl bloodPressureService = new BloodPressureServiceImpl(bloodPressureRepository, fixedClock);

    private final Instant createdTime = Instant.now(fixedClock);

    @Test
    void getBloodPressuresTest_whenDBIsEmpty_thenReturnEmptyList() {
        // GIVEN
        List<BloodPressure> bloodPressures = new ArrayList<>();
        when(bloodPressureRepository.findAll()).thenReturn(bloodPressures);

        // WHEN
        List<BloodPressure> actual = bloodPressureService.getBloodPressures();

        // THEN
        verify(bloodPressureRepository, times(1)).findAll();
        assertThat(actual).isEmpty();
    }

    @Test
    void getBloodPressuresTest_whenDBHasData_thenReturnEmptyList() {
        // GIVEN
        List<BloodPressure> bloodPressures = List.of(
                new BloodPressure(1, 5, 120, 80, createdTime),
                new BloodPressure(2, 5, 125, 75, createdTime)
        );
        when(bloodPressureRepository.findAll()).thenReturn(bloodPressures);

        // WHEN
        List<BloodPressure> actual = bloodPressureService.getBloodPressures();

        // THEN
        List<BloodPressure> expected = List.of(
                new BloodPressure(1, 5, 120, 80, createdTime),
                new BloodPressure(2, 5, 125, 75, createdTime)
        );

        verify(bloodPressureRepository, times(1)).findAll();
        assertEquals(expected, actual);
    }

}