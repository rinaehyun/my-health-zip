package com.myhealthzip.backend.temperature.service;

import com.myhealthzip.backend.temperature.dto.NewTemperatureDto;
import com.myhealthzip.backend.temperature.model.Temperature;

import java.util.List;

public interface TemperatureService {

    List<Temperature> getTemperatures();

    Temperature saveTemperature(NewTemperatureDto newTemperatureDto);
}
