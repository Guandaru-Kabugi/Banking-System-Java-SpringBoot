package com.backend.commonsdto.commons.exception;

import java.util.UUID;

public class OwnerAccessDeniedException extends RuntimeException {
    public OwnerAccessDeniedException() {
        super("Invalid Owner " );
    }
}
