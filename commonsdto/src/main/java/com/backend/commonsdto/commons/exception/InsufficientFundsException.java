package com.backend.commonsdto.commons.exception;

import com.backend.commonsdto.commons.util.ErrorCode;

public class InsufficientFundsException extends BaseException {
    public InsufficientFundsException() {
        super("Insufficient Funds", ErrorCode.UNPROCESSABLE_ENTITY);
    }
}
