package com.arthurtokarev.flightbookingsystem.payload;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;

    private Map<String, String> validationErrors;
}


/* Now all errors will have structure, like this:
{
  "timestamp": "2026-05-20T14:20:00",
  "status": 400,
  "error": "Validation Error",
  "message": "Invalid request",
  "validationErrors": {
    "email": "Invalid email format",
    "password": "Password must be at least 6 characters"
  }
}
 */