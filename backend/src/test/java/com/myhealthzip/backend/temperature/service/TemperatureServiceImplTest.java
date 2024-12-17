package com.myhealthzip.backend.temperature.service;

import com.myhealthzip.backend.temperature.model.Temperature;
import com.myhealthzip.backend.temperature.repository.TemperatureRepository;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TemperatureServiceImplTest {

    private final TemperatureRepository temperatureRepository = mock(TemperatureRepository.class);
    Clock fixedClock = Clock.fixed(Instant.parse("2024-12-17T15:00:00Z"), ZoneId.of("UTC"));

    TemperatureServiceImpl temperatureService = new TemperatureServiceImpl(temperatureRepository);

    private final Instant createdTime = Instant.now(fixedClock);

    @Test
    void getTemperaturesTest_whenDBIsEmpty_thenReturnEmptyList() {
        // GIVEN
        List<Temperature> temperatures = new ArrayList<>();
        when(temperatureRepository.findAll()).thenReturn(temperatures);

        // WHEN
        List<Temperature> actual = temperatureService.getTemperatures();

        // THEN
        verify(temperatureRepository, times(1)).findAll();
        assertThat(actual).isEmpty();
    }

    @Test
    void getTemperaturesTest_whenDBHasData_thenReturnListOfTemperatures() {
        // GIVEN
        List<Temperature> temperatures = List.of(
                new Temperature(1, 1, 36.5, createdTime),
                new Temperature(2, 1, 36.5, createdTime)
        );
        when(temperatureRepository.findAll()).thenReturn(temperatures);

        // WHEN
        List<Temperature> actual = temperatureService.getTemperatures();

        // THEN
        List<Temperature> expected = List.of(
                new Temperature(1, 1, 36.5, createdTime),
                new Temperature(2, 1, 36.5, createdTime)
        );
        verify(temperatureRepository, times(1)).findAll();
        assertEquals(expected, actual);
    }
}