package com.myhealthzip.backend.bloodpressure.service;

import com.myhealthzip.backend.bloodpressure.model.BloodPressure;
import com.myhealthzip.backend.bloodpressure.repository.BloodPressureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloodPressureServiceImpl implements BloodPressureService {

    private final BloodPressureRepository bloodPressureRepository;

    public BloodPressureServiceImpl(BloodPressureRepository bloodPressureRepository) {
        this.bloodPressureRepository = bloodPressureRepository;
    }

    @Override
    public List<BloodPressure> getBloodPressures() {
        return bloodPressureRepository.findAll();
    }
}
