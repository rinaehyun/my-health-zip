package com.myhealthzip.backend.bloodpressure.service;

import com.myhealthzip.backend.bloodpressure.dto.NewBloodPressureDto;
import com.myhealthzip.backend.bloodpressure.exception.BloodPressureInputNotCompletedException;
import com.myhealthzip.backend.bloodpressure.model.BloodPressure;
import com.myhealthzip.backend.bloodpressure.repository.BloodPressureRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void saveBloodPressureTest_whenPayloadIsCorrect_thenReturnBloodPressureEntity() {
        // GIVEN
        NewBloodPressureDto newBloodPressureDto = new NewBloodPressureDto(120, 80);

        when(bloodPressureRepository.save(any(BloodPressure.class))).thenAnswer(invocation -> {
            BloodPressure bloodPressureToSave = invocation.getArgument(0);
            bloodPressureToSave.setBloodPressureId(1);
            return bloodPressureToSave;
        });

        // WHEN
        BloodPressure actual = bloodPressureService.saveBloodPressure(newBloodPressureDto);

        // THEN
        BloodPressure expected = new BloodPressure();
        expected.setBloodPressureId(1);
        expected.setSystolic(newBloodPressureDto.systolic());
        expected.setDiastolic(newBloodPressureDto.diastolic());
        expected.setCreatedTime(createdTime);

        ArgumentCaptor<BloodPressure> bloodPressureCaptor = ArgumentCaptor.forClass(BloodPressure.class);
        verify(bloodPressureRepository, times(1)).save(bloodPressureCaptor.capture());

        BloodPressure capturedBloodPressure = bloodPressureCaptor.getValue();
        assertEquals(expected, capturedBloodPressure);
        assertEquals(expected, actual);
    }

    @Test
    void saveBloodPressureTest_whenPayloadIsNotCorrect_thenThrow() {
        // GIVEN
        NewBloodPressureDto newBloodPressureDto = new NewBloodPressureDto(null, 85);

        // WHEN
        // THEN
        assertThrows(BloodPressureInputNotCompletedException.class,
                () -> bloodPressureService.saveBloodPressure(newBloodPressureDto)
        );
        verify(bloodPressureRepository, never()).save(any(BloodPressure.class));
    }

    @Test
    void deleteBloodPressureTest_whenIdExists_thenDeleteBPEntity() {
        // GIVEN
        Integer bloodPressureId = 2;
        doNothing().when(bloodPressureRepository).deleteById(2);

        // WHEN
        // THEN
        bloodPressureService.deleteBloodPressure(bloodPressureId);
        verify(bloodPressureRepository, times(1)).deleteById(bloodPressureId);
    }
}