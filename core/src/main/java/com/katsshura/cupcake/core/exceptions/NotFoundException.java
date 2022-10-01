package com.katsshura.cupcake.core.exceptions;

import com.katsshura.cupcake.core.enums.exception.ExceptionCodes;

public class NotFoundException extends BusinessException {

    private final String resource;

    public NotFoundException(String resource) {
        super(ExceptionCodes.NOT_FOUND);
        this.resource = resource;
    }

    @Override
    public String getErrorMessage() {
        return String
                .format(super.getExceptionCode().getMessage(), resource);
    }
}
