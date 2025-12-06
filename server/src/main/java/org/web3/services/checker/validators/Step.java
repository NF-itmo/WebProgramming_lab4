package org.web3.services.checker.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StepValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Step {
    String message() default "Invalid step";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    float value();
}
