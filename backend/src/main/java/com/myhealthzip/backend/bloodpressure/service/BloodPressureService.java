package com.myhealthzip.backend.bloodpressure.service;

import com.myhealthzip.backend.bloodpressure.dto.NewBloodPressureDto;
import com.myhealthzip.backend.bloodpressure.dto.UpdateBloodPressureDto;
import com.myhealthzip.backend.bloodpressure.model.BloodPressure;

import java.util.List;

public interface BloodPressureService {

    List<BloodPressure> getBloodPressures();

    BloodPressure saveBloodPressure(NewBloodPressureDto newBloodPressureDto);

    void deleteBloodPressureById(Integer bloodPressureId);

    BloodPressure updateBloodPressureById(Integer bloodPressureId, UpdateBloodPressureDto updateBloodPressureDto);
}
