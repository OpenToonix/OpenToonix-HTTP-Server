package com.juansecu.opentoonix.shared.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BaseResDto<E extends Enum<E>> {
    private final Enum<E> error;
    private final boolean success;
}
