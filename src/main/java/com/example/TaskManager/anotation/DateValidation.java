package com.example.TaskManager.anotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DateTypeValidation.class)
public @interface DateValidation {
    String message() default "{Date is not in correct format in Task.Class}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
