package com.springbootcourse.clear_solution_huza_api.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class NotFutureDateValidator implements ConstraintValidator<NotFutureDate, LocalDate> {
    @Override
    public void initialize(NotFutureDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values should be handled by @NotNull constraint
        }
        LocalDate currentDate = LocalDate.now();
        return !value.isAfter(currentDate);
    }
}
