package com.backend.commonsdto.commons.exception;

import com.backend.commonsdto.commons.util.ErrorCode;

public class VersionMismatchException extends BaseException {
    public VersionMismatchException(String message) { super(message, ErrorCode.VERSION_MISMATCH); }
}
