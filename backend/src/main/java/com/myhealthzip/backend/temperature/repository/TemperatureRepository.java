package com.myhealthzip.backend.temperature.repository;

import com.myhealthzip.backend.temperature.model.Temperature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemperatureRepository extends JpaRepository<Temperature, Integer> {
}
