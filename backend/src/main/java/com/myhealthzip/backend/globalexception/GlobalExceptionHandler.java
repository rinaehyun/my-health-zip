package com.myhealthzip.backend.globalexception;

import com.myhealthzip.backend.bloodpressure.exception.BloodPressureInputNotCompletedException;
import com.myhealthzip.backend.user.exception.UserInputNotCompletedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserInputNotCompletedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleUserInputNotCompletedException(UserInputNotCompletedException ex) {
        return new ErrorMessage(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(BloodPressureInputNotCompletedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleBloodPressureInputNotCompletedException(BloodPressureInputNotCompletedException ex) {
        return new ErrorMessage(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
        );
    }
}
