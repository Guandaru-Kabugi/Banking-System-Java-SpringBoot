package com.backend.commonsdto.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBody<T> {
    private String responseType;
    private Object generatedId;
    private Object generatedCode;
    private T payLoad;
    private Object exception;
    private String exceptionClass;
    private String message;
    private String errorCode;
    private Object warningCode;
    private Object messageTracker;

    public static <T> ResponseBody<T> success(T payload) {
        return ResponseBody.<T>builder()
                .responseType("SUCCESS")
                .payLoad(payload)
                .build();
    }

    public static <T> ResponseBody<T> error(String exceptionClass, String message, String errorCode) {
        return ResponseBody.<T>builder()
                .responseType("ERROR")
                .exceptionClass(exceptionClass)
                .message(message)
                .errorCode(errorCode)
                .build();
    }
}
