package com.juansecu.opentoonix.shared.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CookieModel {
    private final String cookieName;
    private final String cookiePath;
    private final String cookieValue;
}
