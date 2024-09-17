package com.ffc.ffc_be.validation;

import com.ffc.ffc_be.annotation.IsValidClosingInventoryType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClosingInventoryTypeValidator implements ConstraintValidator<IsValidClosingInventoryType, Boolean> {
    @Override
    public boolean isValid(Boolean isManualClosing, ConstraintValidatorContext constraintValidatorContext) {
        if (isManualClosing == null) {
            return false;
        }

        return isManualClosing;
    }

    @Override
    public void initialize(IsValidClosingInventoryType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
