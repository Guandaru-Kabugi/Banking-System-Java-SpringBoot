package com.backend.commonsdto.commons.exception;

import java.util.List;

import com.backend.commonsdto.commons.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.validation.FieldError;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;

/**
 * Centralized error mapping to your commons-dto envelope.
 * Status codes align with your OpenAPI conventions.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<Object>> handleConflict(ConflictException ex) {
        log.error("Conflict error: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = ApiResponse.error(
                "Conflict Errors",
                ex.getMessage(),
                ex.getErrorCode().getCode(),
                HttpStatus.CONFLICT.value()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(response);
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthenticationException(AuthenticationException ex) {
        log.error("Authentication error: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = ApiResponse.error(
                "Authentication Errors",
                ex.getMessage(),
                "INVALID-JWT",
                HttpStatus.UNAUTHORIZED.value()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }
    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleJwtAuthenticationException(JwtAuthenticationException ex) {
        log.error("JWT AUTHENTICATION error: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = ApiResponse.error(
                "JWT AUTHENTICATION Errors",
                ex.getMessage(),
                ex.getErrorCode().getCode(),
                HttpStatus.UNAUTHORIZED.value()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
        log.error("ACCESS DENIED EXCEPTION error: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = ApiResponse.error(
                "Access Denied Exception Errors",
                ex.getMessage(),
                "FORBIDDEN",
                HttpStatus.FORBIDDEN.value()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(response);
    }
    // ---------- Domain Not Found ----------

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccountNotFound(AccountNotFoundException ex) {
        log.error("Account Not Found Exception error: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = ApiResponse.error(
                "Account Not Found Errors",
                ex.getMessage(),
                ex.getErrorCode().getCode(),
                HttpStatus.NOT_FOUND.value()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomerNotFound(CustomerNotFoundException ex) {
        log.error("Customer Not Found error: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = ApiResponse.error(
                "Customer Not Found Errors",
                "CUSTOMER_NOT_FOUND. The customer ID does not exist {}",
                ex.getErrorCode().getCode(),
                HttpStatus.NOT_FOUND.value()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(ConsentNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleConsentNotFound(ConsentNotFoundException ex) {
        log.error("Customer Not Found error: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = ApiResponse.error(
                "CONSENT_MISSING",
                ex.getMessage(),
                ex.getErrorCode().getCode(),
                HttpStatus.NOT_FOUND.value()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        log.error("RESOURCE NOT FOUND: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = ApiResponse.error(
                "RESOURCE-NOT-FOUND",
                ex.getMessage(),
                ex.getErrorCode().getCode(),
                HttpStatus.NOT_FOUND.value()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    // ---------- Business / State ----------

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ApiResponse<Object>> handleInsufficientFunds(InsufficientFundsException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(err("INSUFFICIENT_FUNDS", "Insufficient funds", ex.getMessage()));
    }

    @ExceptionHandler(InvalidTransitionException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidTransition(InvalidTransitionException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(err("INVALID_TRANSITION", ex.getMessage(), ex.getMessage()));
    }

    @ExceptionHandler(VersionMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handleVersionMismatch(VersionMismatchException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(err("VERSION_MISMATCH", "Stale version or ETag", ex.getMessage()));
    }

    

    @ExceptionHandler(UpstreamException.class)
    public ResponseEntity<ApiResponse<Object>> handleUpstream(UpstreamException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(err("UPSTREAM_ERROR", ex.getMessage(), ex.getMessage()));
    }

    // ---------- Validation ----------

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(err("VALIDATION_ERROR", "Constraint violation", ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotReadable(HttpMessageNotReadableException ex) {
        String detail = (ex.getMostSpecificCause() == null)
                ? ex.getMessage()
                : ex.getMostSpecificCause().getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(err("VALIDATION_ERROR", "Malformed JSON request", detail));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleFieldValidation(MethodArgumentNotValidException ex) {
        // Build a single envelope with first message + all field messages in details
        String firstMessage = "Input validation failed";
        String firstField = null;
        StringBuilder all = new StringBuilder();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError fe : fieldErrors) {
            if (firstField == null) {
                firstField = fe.getField();
                if (fe.getDefaultMessage() != null) {
                    firstMessage = fe.getDefaultMessage();
                }
            }
            if (all.length() > 0) all.append("; ");
            all.append(fe.getField()).append(": ").append(fe.getDefaultMessage());
        }

        String details = (firstField != null)
                ? "field=" + firstField + " | " + all.toString()
                : all.toString();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(err("VALIDATION_ERROR", firstMessage, details));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(err("VALIDATION_ERROR", ex.getMessage(), ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(err("VALIDATION_ERROR", ex.getMessage(), ex.getMessage()));
    }

    // ---------- Fallback ----------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        log.error("Unhandled exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(err("INTERNAL_ERROR", "Something went wrong", ex.getMessage()));
    }

}
