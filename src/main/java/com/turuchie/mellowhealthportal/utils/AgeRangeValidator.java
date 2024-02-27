package com.turuchie.mellowhealthportal.utils;
import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
@Component
public class AgeRangeValidator implements ConstraintValidator<AgeRange, Date> {
    private int min;
    private int max;

    @Override
    public void initialize(AgeRange constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Date dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            // Let @NotNull handle null checks
            return true;
        }

        Date currentDate = new Date();
        
        // Check if the date of birth is in the future
        if (dateOfBirth.after(currentDate)) {
            return false;
        }

        long ageInMillis = currentDate.getTime() - dateOfBirth.getTime();
        long ageInYears = ageInMillis / (365L * 24L * 60L * 60L * 1000L);

        return ageInYears >= min && ageInYears <= max;
    }
}
