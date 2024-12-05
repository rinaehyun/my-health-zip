package com.myhealthzip.backend.bloodpressure.controller;

import com.myhealthzip.backend.bloodpressure.dto.NewBloodPressureDto;
import com.myhealthzip.backend.bloodpressure.model.BloodPressure;
import com.myhealthzip.backend.bloodpressure.service.BloodPressureServiceImpl;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public BloodPressure saveBloodPressure(@RequestBody NewBloodPressureDto newBloodPressureDto) {
        return bloodPressureService.saveBloodPressure(newBloodPressureDto);
    }

    @DeleteMapping("/{bloodPressureId}")
    public void deleteBloodPressure(@PathVariable Integer bloodPressureId) {
        bloodPressureService.deleteBloodPressure(bloodPressureId);
    }
}
