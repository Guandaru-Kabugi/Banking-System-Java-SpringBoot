package com.backend.commonsdto.commons.exception;

import com.backend.commonsdto.commons.util.ErrorCode;

public class CustomerNotFoundException extends BaseException {
    public CustomerNotFoundException(String customerId) {
        super("No customer with ID: " + customerId, ErrorCode.NOT_FOUND);
    }
}
