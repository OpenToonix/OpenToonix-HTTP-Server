package com.juansecu.opentoonix.shared.constraints;

import java.lang.annotation.*;

import jakarta.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ValidEnumConstraint {
    Class<? extends Enum<?>> enumClass();

    Class<?>[] groups() default {};

    String message() default "Invalid value. Must be one of {enumClass} values";

    Class<? extends Payload>[] payload() default {};
}
