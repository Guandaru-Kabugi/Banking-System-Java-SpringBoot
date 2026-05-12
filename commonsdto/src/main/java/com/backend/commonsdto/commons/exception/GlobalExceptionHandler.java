package com.backend.commonsdto.commons.exception;

import java.util.List;
import java.util.stream.Collectors;

import com.backend.commonsdto.commons.dto.ApiResponse;
import com.backend.commonsdto.commons.util.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.AuthenticationException;

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
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ApiResponse<Object>> handleAuthenticationException(AuthenticationException ex) {
//        log.error("Authentication error: {}", ex.getMessage(), ex);
//        ApiResponse<Object> response = ApiResponse.error(
//                "Authentication Errors",
//                ex.getMessage(),
//                "INVALID-JWT",
//                HttpStatus.UNAUTHORIZED.value()
//        );
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body(response);
//    }
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

//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ApiResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
//        log.error("ACCESS DENIED EXCEPTION error: {}", ex.getMessage(), ex);
//        ApiResponse<Object> response = ApiResponse.error(
//                "Access Denied Exception Errors",
//                ex.getMessage(),
//                "FORBIDDEN",
//                HttpStatus.FORBIDDEN.value()
//        );
//        return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                .body(response);
//    }
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
        log.error("INSUFFICIENT_FUNDS: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = ApiResponse.error(
                "INSUFFICIENT_FUNDS",
                "Insufficient Funds",
                ex.getErrorCode().getCode(),
                HttpStatus.UNPROCESSABLE_CONTENT.value()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(response);
    }

    @ExceptionHandler(InvalidTransitionException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidTransition(InvalidTransitionException ex) {
        log.error("INVALID TRANSITION: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = ApiResponse.error(
                "INVALID TRANSITION",
                ex.getMessage(),
                ex.getErrorCode().getCode(),
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(VersionMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handleVersionMismatch(VersionMismatchException ex) {
        log.error("VERSION_MISMATCH: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = ApiResponse.error(
                "VERSION_MISMATCH",
                "Stale version or E-Tag",
                ex.getErrorCode().getCode(),
                HttpStatus.CONFLICT.value()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(response);
    }

    

    @ExceptionHandler(UpstreamException.class)
    public ResponseEntity<ApiResponse<Object>> handleUpstream(UpstreamException ex) {
        log.error("UPSTREAM_ERROR: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = ApiResponse.error(
                "UPSTREAM_ERROR",
                ex.getMessage(),
                ex.getErrorCode().getCode(),
                HttpStatus.BAD_GATEWAY.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(response);
    }

    // ---------- Validation ----------

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        log.error("CONSTRAINT_VALIDATION_ERROR: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = ApiResponse.error(
                "VALIDATION_ERROR",
                ex.getMessage(),
                "VALIDATION_ERROR",
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotReadable(HttpMessageNotReadableException ex) {
        log.error("MALFORMED_JSON_REQUEST: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = ApiResponse.error(
                "VALIDATION_ERROR",
                ex.getMostSpecificCause().getMessage(),
                "MALFORMED_JSON_REQUEST",
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.error("Method argument not valid: {}", ex.getMessage(), ex);

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        // First field + message for the top-level message
        String firstField = fieldErrors.isEmpty() ? null : fieldErrors.get(0).getField();
        String firstMessage = fieldErrors.isEmpty() ? "Validation failed"
                : fieldErrors.get(0).getDefaultMessage();

        // All violations joined: "phone: must not be blank; email: must be a valid email"
        String details = fieldErrors.stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));

        String fullMessage = firstField != null
                ? "Validation failed on field '" + firstField + "': " + firstMessage
                : firstMessage;

        ApiResponse<Object> response = ApiResponse.error(
                "ValidationExceptionUnchecked",
                fullMessage,
                ErrorCode.VALIDATION_ERROR.getCode(),
                HttpStatus.BAD_REQUEST.value()
        );

        // Optionally put the details breakdown somewhere in the response
        // e.g. response.setException(details); — if your ApiResponse supports it

        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(ValidationException ex) {
        log.error("VALIDATION_ERROR: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = ApiResponse.error(
                "VALIDATION_ERROR",
                ex.getMessage(),
                "VALIDATION_ERROR",
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        log.error("VALIDATION_ERROR: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = ApiResponse.error(
                "VALIDATION_ERROR",
                ex.getMessage(),
                "VALIDATION_ERROR",
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // ---------- Fallback ----------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        log.error("INTERNAL_ERROR: {}", ex.getMessage(), ex);
        ApiResponse<Object> response = ApiResponse.error(
                "VALIDATION_ERROR",
                ex.getMessage(),
                "INTERNAL_ERROR",
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

}
