package org.web3.services.checker.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StepValidator implements ConstraintValidator<Step, Float> {
    private float step;

    @Override
    public void initialize(Step constraintAnnotation) {
        this.step = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Float value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return value % step < 1e-6;
    }
}
