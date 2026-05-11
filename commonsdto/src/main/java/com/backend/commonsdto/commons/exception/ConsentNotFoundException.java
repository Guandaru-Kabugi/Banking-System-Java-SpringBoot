package com.backend.commonsdto.commons.exception;

import com.backend.commonsdto.commons.util.ErrorCode;

public class ConsentNotFoundException extends BaseException {
    public ConsentNotFoundException(String message) {
        super(message, ErrorCode.CONSENT_MISSING);
    }
}
