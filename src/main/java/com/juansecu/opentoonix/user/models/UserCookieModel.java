package com.juansecu.opentoonix.user.models;

/* --- Third-party modules --- */
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents the information
 * of a cookie that may be sent
 * through the response's body.
 */
@AllArgsConstructor
@Getter
public class UserCookieModel {
    private final String cookieName;
    private final String cookiePath;
    private final String cookieValue;
}
