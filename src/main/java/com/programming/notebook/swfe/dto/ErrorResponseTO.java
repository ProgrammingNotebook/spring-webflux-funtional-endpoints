package com.programming.notebook.swfe.dto;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.programming.notebook.swfe.model.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ErrorResponseTO {

    // Indicates the overall outcome of the request (e.g., "error" or "success").
    private String status;

    // The HTTP status code associated with the error (e.g., 400, 404, 500).
    private HttpStatus statusCode;

    // A unique, application-specific error code (e.g., "INVALID_INPUT",
    // "RESOURCE_NOT_FOUND").
    private ErrorCode code;

    // A brief, human-readable message describing the error.
    private String message;

    // The date and time when the error occurred.
    private ZonedDateTime timestamp;

    // A unique identifier for the specific API request that resulted in the error.
    private String requestId;

    // The URL path of the endpoint where the error occurred.
    private String path;

    // Used to report multiple errors within a single response, especially in cases
    // like validation failures.
    private List<String> errors;
}
