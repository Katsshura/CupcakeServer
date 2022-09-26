package com.katsshura.cupcake.core.exceptions;

import com.katsshura.cupcake.core.enums.exception.ExceptionCodes;

public class IncompatibleValueException extends BusinessException {
    private String message;

    public IncompatibleValueException(final String message) {
        super(ExceptionCodes.INCOMPATIBLE_VALUE);
        this.message = message;
    }

    @Override
    public String getErrorMessage() {
        return message;
    }
}
