package com.juansecu.opentoonix.verificationtokens.dtos.responses;

public record IsValidTokenResDto(
    boolean isTokenValid,
    boolean isUserAuthenticated
) {}
