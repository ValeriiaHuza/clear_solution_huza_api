package com.springbootcourse.clear_solution_huza_api.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotFutureDateValidator.class)
@Documented
public @interface NotFutureDate {
    String message() default "Date must not be in the future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}