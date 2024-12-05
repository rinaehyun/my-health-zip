package com.myhealthzip.backend.bloodpressure.service;

import com.myhealthzip.backend.bloodpressure.dto.NewBloodPressureDto;
import com.myhealthzip.backend.bloodpressure.exception.BloodPressureInputNotCompletedException;
import com.myhealthzip.backend.bloodpressure.model.BloodPressure;
import com.myhealthzip.backend.bloodpressure.repository.BloodPressureRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Service
public class BloodPressureServiceImpl implements BloodPressureService {

    private final BloodPressureRepository bloodPressureRepository;
    private final Clock clock;

    public BloodPressureServiceImpl(BloodPressureRepository bloodPressureRepository, Clock clock) {
        this.bloodPressureRepository = bloodPressureRepository;
        this.clock = clock;
    }

    @Override
    public List<BloodPressure> getBloodPressures() {
        return bloodPressureRepository.findAll();
    }

    @Override
    public BloodPressure saveBloodPressure(NewBloodPressureDto newBloodPressureDto) {
        if (newBloodPressureDto == null || newBloodPressureDto.systolic() == null
                || newBloodPressureDto.diastolic() == null) {
            throw new BloodPressureInputNotCompletedException("The input of blood pressure is not completed.");
        }

        BloodPressure bloodPressureToSave = new BloodPressure();
        bloodPressureToSave.setSystolic(newBloodPressureDto.systolic());
        bloodPressureToSave.setDiastolic(newBloodPressureDto.diastolic());
        bloodPressureToSave.setCreatedTime(Instant.now(clock));

        return bloodPressureRepository.save(bloodPressureToSave);
    }
}
