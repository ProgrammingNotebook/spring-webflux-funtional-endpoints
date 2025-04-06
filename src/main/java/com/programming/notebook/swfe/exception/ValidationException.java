package com.programming.notebook.swfe.exception;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValidationException extends RuntimeException {

    private final List<String> errors;

    public ValidationException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }
}
