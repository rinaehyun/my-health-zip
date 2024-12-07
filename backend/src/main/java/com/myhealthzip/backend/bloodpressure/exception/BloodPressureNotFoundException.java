package com.myhealthzip.backend.bloodpressure.exception;

public class BloodPressureNotFoundException extends RuntimeException {
    public BloodPressureNotFoundException(String message) {
        super(message);
    }
}
