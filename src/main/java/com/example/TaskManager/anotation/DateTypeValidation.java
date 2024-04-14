package com.example.TaskManager.anotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTypeValidation implements ConstraintValidator<DateValidation, Date> {
    private static final String DATE_FORMAT = "MMM d, yyyy hh:mm:ss a"; // Define your desired date format

    @Override
    public void initialize(DateValidation constraintAnnotation) {
        // Initialization
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false); // Set lenient to false to enforce strict date parsing

        try {
            sdf.parse(sdf.format(date));
            return true; // Date format is valid
        } catch (ParseException e) {
            return false; // Date format is invalid
        }
    }
}
