package com.myhealthzip.backend.temperature.controller;

import com.myhealthzip.backend.temperature.dto.NewTemperatureDto;
import com.myhealthzip.backend.temperature.model.Temperature;
import com.myhealthzip.backend.temperature.service.TemperatureServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/temperature")
public class TemperatureController {

    private final TemperatureServiceImpl temperatureService;

    public TemperatureController(TemperatureServiceImpl temperatureService) {
        this.temperatureService = temperatureService;
    }

    @GetMapping
    public List<Temperature> getTemperatures() {
        return temperatureService.getTemperatures();
    }

    @PostMapping
    public Temperature createTemperatre(@RequestBody NewTemperatureDto newTemperatureDto) {
        return temperatureService.saveTemperature(newTemperatureDto);
    }
}
