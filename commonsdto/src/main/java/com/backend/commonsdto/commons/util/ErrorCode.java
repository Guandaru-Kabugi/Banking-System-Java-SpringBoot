package com.backend.commonsdto.commons.util;

import lombok.Getter;

@Getter
public enum ErrorCode {
    VALIDATION_ERROR("VALIDATION_ERROR", "Validation failed"),
    BAD_REQUEST_ERROR("BAD_REQUEST_ERROR", "Bad request"),
    UNAUTHORIZED("UNAUTHORIZED", "Unauthorized access"),
    FORBIDDEN("FORBIDDEN", "Access forbidden"),
    NOT_FOUND("NOT_FOUND", "Resource not found"),
    UNDEFINED_ERROR("UNDEFINED_ERROR", "An undefined error occurred"),
    FILE_SIZE_EXCEEDED("FILE_SIZE_EXCEEDED", "File size exceeds maximum limit"),
    INVALID_FILE_TYPE("INVALID_FILE_TYPE", "Invalid file type"),
    DATABASE_ERROR("DATABASE_ERROR", "Database operation failed"),
    CONSENT_MISSING("CONSENT_MISSING", "Missing consent"),
    CONFLICT_ERROR("CONFLICT_ERROR", "Detail duplication conflicts");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
