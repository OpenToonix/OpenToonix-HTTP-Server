package com.juansecu.opentoonix.verificationtokens;

public class VerificationTokensConstants {
    public static final int HOURS_OF_EXPIRING_EMAIL_VERIFICATION_TOKEN = 24;
    public static final String IS_ALREADY_USED_TOKEN_VIEW_ATTRIBUTE = "isAlreadyUsedToken";
    public static final String IS_INVALID_TOKEN_REQUEST_ATTRIBUTE = "isInvalidToken";
    public static final String IS_VALID_TOKEN_VIEW_ATTRIBUTE_KEY = "isValidToken";
    public static final String MESSAGE_VIEW_ATTRIBUTE_KEY = "message";
    public static final int MINUTES_OF_EXPIRING_DELETE_ACCOUNT_TOKEN = 10;
    public static final int MINUTES_OF_EXPIRING_RESET_PASSWORD_TOKEN = 10;
    public static final String TOKEN_TYPE_REQUEST_PARAMETER = "type";
    public static final String VERIFICATION_TOKEN_REQUEST_ATTRIBUTE = "verificationToken";
    public static final String VERIFICATION_TOKEN_TYPE_REQUEST_ATTRIBUTE = "verificationTokenType";
    public static final String VERIFICATION_TOKEN_VIEW_NAME = "verification-token";

    private VerificationTokensConstants() {}
}
