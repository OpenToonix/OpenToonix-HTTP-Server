package com.juansecu.opentoonix.verificationtokens.enums;

public enum EVerificationTokenType {
    DELETE_ACCOUNT_TOKEN("DELETE_ACCOUNT_TOKEN"),
    EMAIL_VERIFICATION_TOKEN("EMAIL_VERIFICATION_TOKEN"),
    RESET_PASSWORD_TOKEN("RESET_PASSWORD_TOKEN");

    final String verificationTokenType;

    EVerificationTokenType(final String verificationTokenType) {
        this.verificationTokenType = verificationTokenType;
    }
}
