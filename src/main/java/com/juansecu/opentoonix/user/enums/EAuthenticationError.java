package com.juansecu.opentoonix.user.enums;

public enum EAuthenticationError {
    INVALID_AUTHENTICATION_COOKIE("INVALID_AUTHENTICATION_COOKIE"),
    NO_AUTHENTICATION_COOKIE("NO_AUTHENTICATION_COOKIE");

    public final String errorMessage;

    EAuthenticationError(final String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
