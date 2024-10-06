package com.juansecu.opentoonix.auth.enums;

import lombok.Getter;

@Getter
public enum EBasicRole {
    ADMIN("ADMIN"),
    MODERATOR("MODERATOR"),
    USER("USER");

    final String role;

    EBasicRole(final String role) {
        this.role = role;
    }
}
