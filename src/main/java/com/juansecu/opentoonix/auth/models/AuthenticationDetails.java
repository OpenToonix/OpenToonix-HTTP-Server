package com.juansecu.opentoonix.auth.models;

import com.juansecu.opentoonix.users.models.entities.UserEntity;

public record AuthenticationDetails (
    String token,
    UserEntity user
) {}
