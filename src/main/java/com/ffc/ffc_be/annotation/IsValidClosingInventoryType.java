package com.ffc.ffc_be.annotation;

import com.ffc.ffc_be.validation.ClosingInventoryTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ClosingInventoryTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidClosingInventoryType {
    String message() default "Is manual closing must be true";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
