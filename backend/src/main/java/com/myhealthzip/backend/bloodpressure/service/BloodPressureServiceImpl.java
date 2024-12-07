package com.myhealthzip.backend.bloodpressure.service;

import com.myhealthzip.backend.bloodpressure.dto.NewBloodPressureDto;
import com.myhealthzip.backend.bloodpressure.dto.UpdateBloodPressureDto;
import com.myhealthzip.backend.bloodpressure.exception.BloodPressureInputNotCompletedException;
import com.myhealthzip.backend.bloodpressure.exception.BloodPressureNotFoundException;
import com.myhealthzip.backend.bloodpressure.model.BloodPressure;
import com.myhealthzip.backend.bloodpressure.repository.BloodPressureRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

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

    @Override
    public void deleteBloodPressureById(Integer bloodPressureId) {
        bloodPressureRepository.deleteById(bloodPressureId);
    }

    @Override
    public BloodPressure updateBloodPressureById(Integer bloodPressureId, UpdateBloodPressureDto updateBloodPressureDto) {

        BloodPressure bloodPressureToUpdate = bloodPressureRepository.findById(bloodPressureId)
                .orElseThrow(() -> new BloodPressureNotFoundException("Blood Pressure with id " + bloodPressureId + " cannot be found."));

        bloodPressureToUpdate.setSystolic(updateBloodPressureDto.systolic());
        bloodPressureToUpdate.setDiastolic(updateBloodPressureDto.diastolic());

        return bloodPressureRepository.save(bloodPressureToUpdate);
    }
}
