package com.turuchie.mellowhealthportal.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeRangeValidator.class)
public @interface AgeRange {
    String message() default "Invalid age range";
    int min() default 0;
    int max() default 120;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
