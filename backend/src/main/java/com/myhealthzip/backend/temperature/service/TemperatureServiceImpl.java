package com.myhealthzip.backend.temperature.service;

import com.myhealthzip.backend.temperature.model.Temperature;
import com.myhealthzip.backend.temperature.repository.TemperatureRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;

@Service
public class TemperatureServiceImpl implements TemperatureService{

    private final TemperatureRepository temperatureRepository;

    public TemperatureServiceImpl(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    public List<Temperature> getTemperatures() {
        return temperatureRepository.findAll();
    }
}
