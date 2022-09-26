package com.katsshura.cupcake.core.exceptions;

import com.katsshura.cupcake.core.enums.exception.ExceptionCodes;
import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {
    private final ExceptionCodes exceptionCode;

    protected BusinessException(final ExceptionCodes exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public abstract String getErrorMessage();
}
