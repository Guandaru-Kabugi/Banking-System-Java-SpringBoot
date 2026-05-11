package com.backend.commonsdto.commons.exception;

import com.backend.commonsdto.commons.util.ErrorCode;

import java.util.UUID;

public class AccountNotFoundException extends BaseException {
    public AccountNotFoundException(UUID accountId) {
        super("No account with ID: " + accountId, ErrorCode.NOT_FOUND);
    }
}
