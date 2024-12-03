package com.myhealthzip.backend.bloodpressure.repository;

import com.myhealthzip.backend.bloodpressure.model.BloodPressure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodPressureRepository extends JpaRepository<BloodPressure, Integer> {
}
