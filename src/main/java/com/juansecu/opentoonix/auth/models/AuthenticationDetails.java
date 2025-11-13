package com.juansecu.opentoonix.auth.models;

import com.juansecu.opentoonix.users.models.entities.UserEntity;

public record AuthenticationDetails (
    boolean isUserAuthenticated,
    String token,
    UserEntity user
) {}
