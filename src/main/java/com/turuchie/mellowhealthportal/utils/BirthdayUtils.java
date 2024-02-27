package com.turuchie.mellowhealthportal.utils;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
@Component
public class BirthdayUtils {

    public static int calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of birth cannot be null");
        }
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public static boolean isValidBirthYear(int birthYear) {
        int currentYear = LocalDate.now().getYear();
        int minValidYear = currentYear - 150; // 150 years ago
        int maxValidYear = currentYear; // Current year

        return birthYear >= minValidYear && birthYear <= maxValidYear && birthYear <= currentYear;
    }

    public static boolean isValidBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            return false; // Null birth date is invalid
        }

        LocalDate currentDate = LocalDate.now();
        int minValidYear = currentDate.getYear() - 150;

        // Check if birth date is in the past, not in the future
        if (birthDate.isAfter(currentDate)) {
            return false;
        }

        // Check if the birth year is within the specified range
        if (birthDate.getYear() < minValidYear) {
            return false;
        }

        // Check if the birth date is in the future month of the current year
        if (birthDate.getYear() == currentDate.getYear() &&
                (birthDate.getMonthValue() > currentDate.getMonthValue() ||
                        (birthDate.getMonthValue() == currentDate.getMonthValue() &&
                                birthDate.getDayOfMonth() > currentDate.getDayOfMonth()))) {
            return false;
        }

        return true;
    } 

    private boolean isValidBirthDateAndYear(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();

        // Check if the birth date is on or before the current date
        if (birthDate.isAfter(currentDate)) {
            return false;
        }

        // Check if the birth date's year is within the specified range (up to 150 years ago)
        int minValidYear = currentDate.minusYears(150).getYear();
        if (birthDate.getYear() < minValidYear || birthDate.getYear() > currentDate.getYear()) {
            return false;
        }

        // Check if the birth date's month is after the current month
        if (birthDate.getMonthValue() > currentDate.getMonthValue()) {
            return false;
        }

        // Check if the birth date's month is the same as the current month but the day is after the current day
        return !(birthDate.getMonthValue() == currentDate.getMonthValue() && birthDate.getDayOfMonth() > currentDate.getDayOfMonth());
    }   
    
    public void calculateAge(Model model, Patient patient, BindingResult bindingResult) {
        // Null check for date of birth
        LocalDate dateOfBirth = patient.getDateOfBirth();
        if (dateOfBirth == null) {
            // Handle the case where date of birth is null
            bindingResult.rejectValue("dateOfBirth", "NotNull", "Date of birth cannot be null");
            return;
        }

        // Check if the birth date and year are valid
        if (!isValidBirthDateAndYear(dateOfBirth)) {
            bindingResult.rejectValue("dateOfBirth", "InvalidDate", "Invalid birth date");
        }

        // Calculate patient age
        int patientAge = Period.between(dateOfBirth, LocalDate.now()).getYears();

        // Calculate account length (years since creation date)
        long accountLengthInYears = Period.between(patient.getCreatedAt().toLocalDate(), LocalDate.now()).getYears();

        // Set attributes for the age and account length
        model.addAttribute("age", patientAge);
        model.addAttribute("accountLengthInYears", accountLengthInYears);
    }
}
