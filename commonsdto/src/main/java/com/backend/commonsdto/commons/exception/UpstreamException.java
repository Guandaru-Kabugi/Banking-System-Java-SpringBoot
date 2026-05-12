package com.backend.commonsdto.commons.exception;

import com.backend.commonsdto.commons.util.ErrorCode;

public class UpstreamException extends BaseException {
    public UpstreamException(String message) { super(message, ErrorCode.UPSTREAM_ERROR); }
}
