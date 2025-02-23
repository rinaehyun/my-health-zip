package com.myhealthzip.backend.temperature.service;

import com.myhealthzip.backend.temperature.dto.NewTemperatureDto;
import com.myhealthzip.backend.temperature.model.Temperature;
import com.myhealthzip.backend.temperature.repository.TemperatureRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Service
public class TemperatureServiceImpl implements TemperatureService{

    private final TemperatureRepository temperatureRepository;
    private final Clock clock;

    public TemperatureServiceImpl(TemperatureRepository temperatureRepository, Clock clock) {
        this.temperatureRepository = temperatureRepository;
        this.clock = clock;
    }

    public List<Temperature> getTemperatures() {
        return temperatureRepository.findAll();
    }

    public Temperature saveTemperature(NewTemperatureDto newTemperatureDto) {
        Temperature temperatureToSave = new Temperature();
        temperatureToSave.setTemperature(newTemperatureDto.temperature());
        temperatureToSave.setCreatedTime(Instant.now(clock));

        return temperatureRepository.save(temperatureToSave);
    }
}
