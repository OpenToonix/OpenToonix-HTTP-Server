package com.juansecu.opentoonix.auth.enums;

public enum EAuthenticationError {
    INVALID_AUTHENTICATION_COOKIE("INVALID_AUTHENTICATION_COOKIE");

    final String errorMessage;

    EAuthenticationError(final String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
