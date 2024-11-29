package com.myhealthzip.backend.globalexception;

import java.time.Instant;

public record ErrorMessage(
        Instant timestamp,
        int status,
        String message
) {
}
