package com.myhealthzip.backend.temperature.controller;

import com.myhealthzip.backend.temperature.model.Temperature;
import com.myhealthzip.backend.temperature.service.TemperatureServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
