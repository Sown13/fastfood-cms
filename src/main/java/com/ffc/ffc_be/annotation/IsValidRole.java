package com.ffc.ffc_be.annotation;

import com.ffc.ffc_be.validation.RoleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoleValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidRole {
    String message() default "Invalid ROLE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
