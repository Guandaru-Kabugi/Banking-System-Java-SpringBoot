package com.backend.commonsdto.commons.exception;

import com.backend.commonsdto.commons.util.ErrorCode;

public class JwtAuthenticationException extends BaseException {
    public JwtAuthenticationException(String message) {
        super(message, ErrorCode.UNAUTHORIZED);
    }
}
