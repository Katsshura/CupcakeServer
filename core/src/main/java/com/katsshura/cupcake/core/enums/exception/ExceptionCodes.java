package com.katsshura.cupcake.core.enums.exception;

import lombok.Getter;

@Getter
public enum ExceptionCodes {
    ALREADY_EXISTS("The resource [%s] already exists in our server!"),
    INCOMPATIBLE_VALUE("The value [%s} is incompatible!"),

    NOT_FOUND("The resource [%s] was not found!");

    private final String message;

    ExceptionCodes(final String message) {
        this.message = message;
    }
}
