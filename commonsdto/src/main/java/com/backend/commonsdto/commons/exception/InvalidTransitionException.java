package com.backend.commonsdto.commons.exception;

import com.backend.commonsdto.commons.util.ErrorCode;

public class InvalidTransitionException extends BaseException {
    public InvalidTransitionException(String message)
    { super(message, ErrorCode.INVALID_TRANSITION); }
}
