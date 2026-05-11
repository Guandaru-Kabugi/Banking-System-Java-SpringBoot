package com.backend.commonsdto.commons.exception;

import com.backend.commonsdto.commons.util.ErrorCode;

public class ConflictException extends BaseException {
    public ConflictException(String message) { super(message, ErrorCode.CONFLICT_ERROR); }
}
