package com.juansecu.opentoonix.auth.enums;

public enum ELoggingInError {
    USER_DISABLED("USER_DISABLED"),
    USER_NOT_EXISTS("USER_NOT_EXISTS"),
    USER_WRONG_PASSWORD("USER_WRONG_PASSWORD");

    final String errorMessage;

    ELoggingInError(final String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
