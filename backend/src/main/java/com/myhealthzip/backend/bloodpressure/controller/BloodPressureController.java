package com.myhealthzip.backend.bloodpressure.controller;

import com.myhealthzip.backend.bloodpressure.model.BloodPressure;
import com.myhealthzip.backend.bloodpressure.service.BloodPressureServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/blood-pressure")
public class BloodPressureController {

    private final BloodPressureServiceImpl bloodPressureService;

    BloodPressureController(BloodPressureServiceImpl bloodPressureService) {
        this.bloodPressureService = bloodPressureService;
    }

    @GetMapping
    public List<BloodPressure> getBloodPressure() {
        return bloodPressureService.getBloodPressures();
    }
}
