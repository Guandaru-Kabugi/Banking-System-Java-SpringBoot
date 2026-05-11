package com.backend.commonsdto.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse <T> {

    private Map<String, Object> headers;
    private ResponseBody<T> body;
    private int statusCodeValue;
    private String statusCode;


    public static <T> ApiResponse<T> success(T payload) {
        return ApiResponse.<T>builder()
                .headers(new HashMap<>())
                .body(ResponseBody.success(payload))
                .statusCodeValue(200)
                .statusCode("OK")
                .build();
    }

    public static <T> ApiResponse<T> error(String exceptionClass, String message, String errorCode, int statusCode) {
        return ApiResponse.<T>builder()
                .headers(new HashMap<>())
                .body(ResponseBody.error(exceptionClass, message, errorCode))
                .statusCodeValue(statusCode)
                .statusCode(getStatusText(statusCode))
                .build();
    }

    private static String getStatusText(int statusCode) {
        return switch (statusCode) {
            case 400 -> "BAD_REQUEST";
            case 401 -> "UNAUTHORIZED";
            case 409 -> "CONFLICT";
            case 403 -> "FORBIDDEN";
            case 404 -> "NOT_FOUND";
            case 500 -> "INTERNAL_SERVER_ERROR";
            default -> "UNKNOWN";
        };
    }
}
