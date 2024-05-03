package com.juansecu.opentoonix.core.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractHttpException extends RuntimeException implements IHttpException {
    private final Enum<?> error;

    public final Enum<?> getError() {
        return this.error;
    }
}
