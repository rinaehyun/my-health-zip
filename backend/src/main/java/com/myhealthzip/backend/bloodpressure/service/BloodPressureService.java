package com.myhealthzip.backend.bloodpressure.service;

import com.myhealthzip.backend.bloodpressure.model.BloodPressure;

import java.util.List;

public interface BloodPressureService {
    List<BloodPressure> getBloodPressures();
}
