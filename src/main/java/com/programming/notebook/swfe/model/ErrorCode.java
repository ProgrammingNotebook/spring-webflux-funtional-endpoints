package com.programming.notebook.swfe.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Indicates that the request was malformed or contained invalid data.
    INVALID_INPUT("INVALID_INPUT"),

    // Indicates that the requested resource was not found.
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND"),

    // Indicates that the server encountered an unexpected condition.
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),

    // Indicates that the request was not authorized.
    UNAUTHORIZED("UNAUTHORIZED"),

    // Indicates that the request was forbidden.
    FORBIDDEN("FORBIDDEN");

    private final String code;
}
