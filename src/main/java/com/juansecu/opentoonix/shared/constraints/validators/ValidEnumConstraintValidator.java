package com.juansecu.opentoonix.shared.constraints.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.juansecu.opentoonix.shared.constraints.ValidEnumConstraint;

public class ValidEnumConstraintValidator implements ConstraintValidator<ValidEnumConstraint, Object> {
    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(final ValidEnumConstraint constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(
        final Object value,
        final ConstraintValidatorContext context
    ) {
        if (value == null) return false;

        if (enumClass.isInstance(value)) return true;

        if (value instanceof String strValue) {
            for (Enum<?> constant : enumClass.getEnumConstants()) {
                if (constant.name().equals(strValue)) return true;
            }
        } else if (value instanceof Integer intValue) {
            for (Enum<?> constant : enumClass.getEnumConstants()) {
                if (constant.ordinal() == intValue) return true;
            }
        }

        return false;
    }
}
