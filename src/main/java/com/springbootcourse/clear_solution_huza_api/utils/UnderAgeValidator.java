package com.springbootcourse.clear_solution_huza_api.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.Period;

public class UnderAgeValidator implements ConstraintValidator<UnderAge, LocalDate> {

    @Value("${user.validAge}")
    private int VALID_AGE;
    private String message;
    @Override
    public void initialize(UnderAge constraintAnnotation) {
        // Set default message if not provided
        if (message == null || message.isEmpty()) {
            this.message = "User must be higher " + VALID_AGE + " years old";
        }
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle this
        }

        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(value, currentDate);

        if(period.getYears() >= VALID_AGE){
            return true;
        }
        else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("User must be at least " + VALID_AGE + " years old" )
                    .addConstraintViolation();
            return false;
        }
    }
}
