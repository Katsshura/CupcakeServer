package com.katsshura.cupcake.core.exceptions;

import com.katsshura.cupcake.core.enums.exception.ExceptionCodes;

public class AlreadyExistsException extends BusinessException {
    private final String resource;

    public AlreadyExistsException(final String resource) {
        super(ExceptionCodes.ALREADY_EXISTS);
        this.resource = resource;
    }

    @Override
    public String getErrorMessage() {
        return String.format(
                super.getExceptionCode().getMessage(), this.resource
        );
    }
}
