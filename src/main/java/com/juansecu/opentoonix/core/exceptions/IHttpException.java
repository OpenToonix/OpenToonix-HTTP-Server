package com.juansecu.opentoonix.core.exceptions;

public interface IHttpException<E extends Enum<E>> {
    Enum<E> getError();
}
