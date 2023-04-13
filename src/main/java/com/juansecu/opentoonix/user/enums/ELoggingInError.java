package com.juansecu.opentoonix.user.enums;

public enum ELoggingInError {
    USER_DISABLED("USER_DISABLED"),
    USER_NOT_EXISTS("USER_NOT_EXISTS"),
    USER_WRONG_PASSWORD("USER_WRONG_PASSWORD");

    public final String ERROR_MESSAGE;

    ELoggingInError(final String errorMessage) {
        this.ERROR_MESSAGE = errorMessage;
    }
}
